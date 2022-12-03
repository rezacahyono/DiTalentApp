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
import com.capstone.ditalent.databinding.ActivityAuthBinding
import com.capstone.ditalent.databinding.DialogBottomChooseRoleBinding
import com.capstone.ditalent.model.Role
import com.capstone.ditalent.ui.auth.fragments.login.LoginViewModel
import com.capstone.ditalent.ui.boarding.BoardingActivity
import com.capstone.ditalent.ui.boarding.BoardingViewModel
import com.capstone.ditalent.ui.talent.activities.TalentActivity
import com.capstone.ditalent.ui.umkm.activities.UmkmActivity
import com.capstone.ditalent.utils.Utilities
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding as ActivityAuthBinding
    private var _bindingBottomSheetRole: DialogBottomChooseRoleBinding? = null
    private val bindingBottomSheetRole get() = _bindingBottomSheetRole as DialogBottomChooseRoleBinding
    private lateinit var navController: NavController

    private lateinit var googleSignInClient: GoogleSignInClient
    private var role: Role? = null

    private val boardingViewModel: BoardingViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition { boardingViewModel.isFirstRun.value == null }
            setKeepOnScreenCondition { loginViewModel.firebaseUser.value != null }
        }

        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        boardingViewModel.isFirstRun.observe(this) { isFirstRun ->
            boardingViewModel.updateIsFirstRun(false)
            if (isFirstRun) {
                val intent = Intent(this, BoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        loginViewModel.firebaseUser.observe(this) { state ->
            val currentUser = state.firebaseUser
            currentUser?.uid?.let { id ->
                loginViewModel.getUser(id).observe(this) { state ->
                    state.user?.let { user ->
                        when (user.role) {
                            Role.TALENT.toString() -> navigateToTalent()
                            Role.UMKM.toString() -> navigateToUmkm()
                        }
                    }
                }
            }
        }

        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_auth) as NavHostFragment
        navController = navHostFragment.navController

    }

    fun signInWithGoogle() {
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
                val signInIntent = googleSignInClient.signInIntent
                resultLauncher.launch(signInIntent)
                _bindingBottomSheetRole = null
                dismiss()
            }
            negativeButton(R.string.cancel) {
                _bindingBottomSheetRole = null
                dismiss()
            }
        }
    }


    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { token ->
                    role?.let {
                        val credential = GoogleAuthProvider.getCredential(token, null)
                        loginViewModel.loginWithGoogle(credential, it.toString())
                            .observe(this) { state ->
                                when {
                                    state.isSuccess -> {
                                        Utilities.showSnackBar(this, "Success", binding.root)
                                    }
                                    state.isError -> {
                                        Utilities.showSnackBar(this, "Error", binding.root)
                                    }
                                }
                            }
                    }
                }
            } catch (e: ApiException) {
                Utilities.showSnackBar(
                    this,
                    getString(R.string.text_result_login_failed),
                    binding.root
                )
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