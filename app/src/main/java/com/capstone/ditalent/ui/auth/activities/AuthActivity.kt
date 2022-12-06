package com.capstone.ditalent.ui.auth.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.capstone.ditalent.R
import com.capstone.ditalent.component.LoadingDialog
import com.capstone.ditalent.databinding.ActivityAuthBinding
import com.capstone.ditalent.databinding.DialogBottomChooseRoleBinding
import com.capstone.ditalent.model.Role
import com.capstone.ditalent.ui.auth.fragments.login.LoginViewModel
import com.capstone.ditalent.ui.boarding.BoardingActivity
import com.capstone.ditalent.ui.boarding.BoardingViewModel
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
class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding as ActivityAuthBinding

    private var _bindingBottomSheetRole: DialogBottomChooseRoleBinding? = null
    private val bindingBottomSheetRole get() = _bindingBottomSheetRole as DialogBottomChooseRoleBinding

    private lateinit var navController: NavController

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    private var role: Role? = null

    private lateinit var loadingDialog: LoadingDialog

    private val boardingViewModel: BoardingViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.apply {
            setKeepOnScreenCondition {
                loginViewModel.currentUser.value != null
            }

        }

        boardingViewModel.isFirstRunBoarding.observe(this) { isFirstRun ->
            boardingViewModel.updateIsFirstRunBoarding(false)
            if (isFirstRun) {
                val intent = Intent(this, BoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        loginViewModel.getUser.observe(this) { user ->
            when (user.role) {
                Role.TALENT.toString() -> navigateToTalent()
                Role.UMKM.toString() -> navigateToUmkm()
            }
        }

        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingDialog = LoadingDialog(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_auth) as NavHostFragment
        navController = navHostFragment.navController

    }

    fun signInWithGoogle() {
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
                    loginViewModel.loginUiState.observe(this@AuthActivity) { state ->
                        when {
                            state.isError -> {
                                loadingDialog.hideDialog()
                                showSnackBar(
                                    this@AuthActivity,
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
                    getString(R.string.text_result_login_failed),
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
                loginViewModel.addUser(role!!.toString()).observe(this@AuthActivity) { state ->
                    when {
                        state.isError -> {
                            loadingDialog.hideDialog()
                            showSnackBar(
                                this@AuthActivity,
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
                loginViewModel.logoutUiState.observe(this@AuthActivity) { state ->
                    if (state.isError) {
                        showSnackBar(
                            this@AuthActivity,
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}