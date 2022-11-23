package com.capstone.ditalent.ui.auth.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.capstone.ditalent.AuthNavGraphDirections
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.ActivityAuthBinding
import com.capstone.ditalent.model.Role
import com.capstone.ditalent.ui.auth.fragments.login.LoginViewModel
import com.capstone.ditalent.ui.boarding.BoardingActivity
import com.capstone.ditalent.ui.boarding.BoardingViewModel
import com.capstone.ditalent.ui.talent.activities.TalentActivity
import com.capstone.ditalent.ui.umkm.activities.UmkmActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val boardingViewModel: BoardingViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityAuthBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition { boardingViewModel.isFirstRun.value == null }
        }

        super.onCreate(savedInstanceState)


        boardingViewModel.isFirstRun.observe(this) { isFirstRun ->
            boardingViewModel.updateIsFirstRun(false)
            if (isFirstRun) {
                val intent = Intent(this, BoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        loginViewModel.userPref.observe(this) { user ->
            if (user.isLogin) {
                when (user.role) {
                    Role.TALENT -> navigateToTalent()
                    Role.UMKM -> navigateToUmkm()

                }
            }
        }


        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_auth) as NavHostFragment
        navController = navHostFragment.navController

        val route = intent.getStringExtra(BoardingActivity.ROUTE)
        if (route != null) {
            when (route) {
                BoardingActivity.ROUTE_LOGIN -> {
                    navigateToLogin(navController)
                }
                BoardingActivity.ROUTE_REGISTER -> {
                    navigateToRegister(navController)
                }
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

    private fun navigateToLogin(navController: NavController) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.auth_nav_graph, true)
            .setLaunchSingleTop(true)
            .build()
        navController.navigate(R.id.login_nav, null, navOptions)
    }

    private fun navigateToRegister(navController: NavController) {
        val directions = AuthNavGraphDirections.actionToRegister()
        navController.navigate(directions)
    }
}