package com.capstone.ditalent.ui.boarding

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.capstone.ditalent.R
import com.capstone.ditalent.adapter.BoardingAdapter
import com.capstone.ditalent.component.LoadingDialog
import com.capstone.ditalent.databinding.ActivityBoardingBinding
import com.capstone.ditalent.databinding.DialogBottomChooseRoleBinding
import com.capstone.ditalent.model.Boarding
import com.capstone.ditalent.model.Role
import com.capstone.ditalent.ui.auth.activities.AuthActivity
import com.capstone.ditalent.ui.auth.fragments.login.LoginViewModel
import com.capstone.ditalent.ui.talent.activities.TalentActivity
import com.capstone.ditalent.ui.umkm.activities.UmkmActivity
import com.capstone.ditalent.utils.UiText
import com.capstone.ditalent.utils.Utilities.observeOnce
import com.capstone.ditalent.utils.Utilities.showSnackBar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BoardingActivity : AppCompatActivity() {
    private var _binding: ActivityBoardingBinding? = null
    private val binding get() = _binding as ActivityBoardingBinding

    private var _bindingBottomSheetRole: DialogBottomChooseRoleBinding? = null
    private val bindingBottomSheetRole get() = _bindingBottomSheetRole as DialogBottomChooseRoleBinding

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var loadingDialog: LoadingDialog

    private val loginViewModel: LoginViewModel by viewModels()

    private var role: Role? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUi()

        loadingDialog = LoadingDialog(this)

        loginViewModel.getUser.observe(this) { user ->
            when (user.role) {
                Role.TALENT.toString() -> navigateToTalent()
                Role.UMKM.toString() -> navigateToUmkm()
            }
        }

        setupSlideBoarding()
    }

    private fun setupSlideBoarding() {

        val boardingOne = Boarding(
            image = R.drawable.slide_board_1,
            title = R.string.title_slide_one,
            description = R.string.description_slide_one
        )
        val boardingTwo = Boarding(
            image = R.drawable.slide_board_2,
            title = R.string.title_slide_two,
            description = R.string.description_slide_two
        )
        val boardingThree = Boarding(
            image = R.drawable.slide_board_3,
            title = R.string.title_slide_three,
            description = R.string.description_slide_three
        )

        val listBoarding = listOf(boardingOne, boardingTwo, boardingThree)
        val boardingAdapter = BoardingAdapter()
        boardingAdapter.submitList(listBoarding)

        val changePage = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navigateHandlingSlide(position, listBoarding.size - 1)
                setupTextButton(position == listBoarding.size - 1)
            }
        }

        binding.pageBoarding.apply {
            adapter = boardingAdapter
            registerOnPageChangeCallback(changePage)
            isUserInputEnabled = false
        }

        binding.indicatorVp.attachTo(binding.pageBoarding)
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }


    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { token ->
                    val credential = GoogleAuthProvider.getCredential(token, null)
                    loginViewModel.loginWithGoogle(credential)
                    loginViewModel.loginUiState.observe(this@BoardingActivity) { state ->
                        when {
                            state.isError -> {
                                loadingDialog.hideDialog()
                                showSnackBar(
                                    this@BoardingActivity,
                                    getString((state.message as UiText.StringResource).id),
                                    binding.root
                                )
                            }
                            state.isLoading -> loadingDialog.showDialog()
                            state.isSuccess -> loadingDialog.hideDialog()

                        }
                    }
                    loginViewModel.checkUserIsExists.observeOnce(this) { exists ->
                        if (!exists) {
                            showDialogChooseRole()
                        }
                    }
                }
            } catch (e: ApiException) {
                showSnackBar(
                    this,
                    getString(R.string.text_message_error_something),
                    binding.root
                )
            }
        }
    }

    private fun showDialogChooseRole() {
        _bindingBottomSheetRole = DialogBottomChooseRoleBinding.inflate(layoutInflater)
        MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            cornerRadius(res = R.dimen.large_shape_rounded)
            customView(view = bindingBottomSheetRole.root)
            positiveButton(R.string.next) {
                role =
                    if (bindingBottomSheetRole.rgChooseUser.checkedRadioButtonId == R.id.rb_choose_umkm) {
                        Role.UMKM
                    } else {
                        Role.TALENT
                    }
                loginViewModel.addUser(role!!.toString()).observe(this@BoardingActivity) { state ->
                    when {
                        state.isError -> {
                            loadingDialog.hideDialog()
                            showSnackBar(
                                this@BoardingActivity,
                                getString((state.message as UiText.StringResource).id),
                                binding.root
                            )
                        }
                        state.isLoading -> loadingDialog.showDialog()
                        state.isSuccess -> loadingDialog.hideDialog()
                    }
                }
                _bindingBottomSheetRole = null
                dismiss()
            }
            negativeButton(R.string.cancel) {
                loginViewModel.logout()
                loginViewModel.logoutUiState.observe(this@BoardingActivity) { state ->
                    if (state.isError) {
                        showSnackBar(
                            this@BoardingActivity,
                            getString((state.message as UiText.StringResource).id),
                            binding.root
                        )
                    }
                }
                _bindingBottomSheetRole = null
                dismiss()
            }
        }
    }

    internal fun navigateHandlingSlide(position: Int, lastPage: Int) {
        binding.apply {
            btnNext.setOnClickListener {
                if (position == lastPage) {
                    signInWithGoogle()
                } else {
                    binding.pageBoarding.currentItem = position + 1
                }
            }
            btnSkip.setOnClickListener {
                if (position != lastPage) {
                    binding.pageBoarding.currentItem = lastPage
                } else {
                    navigateToAuth()
                }
            }
        }
    }

    private fun setupTextButton(lastPage: Boolean) {
        val textButtonNext =
            if (lastPage) getString(R.string.text_button_login_using_google) else getString(R.string.text_button_next)
        val textButtonSkip =
            if (lastPage) getString(R.string.text_button_login_now) else getString(R.string.text_button_skip)
        val iconButtonGoogle: Drawable? =
            if (lastPage) ContextCompat.getDrawable(this, R.drawable.ic_google) else null
        binding.apply {
            btnNext.apply {
                text = textButtonNext
                if (lastPage) {
                    setCompoundDrawablesWithIntrinsicBounds(iconButtonGoogle, null, null, null)
                }
            }
            btnSkip.text = textButtonSkip
        }
    }

    private fun navigateToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToTalent() {
        val intent = Intent(this, TalentActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToUmkm() {
        val intent = Intent(this, UmkmActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun hideSystemUi() {
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        val statusBar = WindowInsetsCompat.Type.statusBars()
        val navBars = WindowInsetsCompat.Type.navigationBars()
        insetsController.hide(statusBar)
        insetsController.hide(navBars)
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}