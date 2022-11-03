package com.capstone.ditalent.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.capstone.ditalent.MainNavGraphDirections
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.ActivityMainBinding
import com.capstone.ditalent.ui.activities.boarding.BoardingActivity
import com.capstone.ditalent.ui.activities.boarding.BoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Temporary boarding flow
    private val boardingViewModel: BoardingViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardingViewModel.isFirstRun.observe(this) { isFirstRun ->
            boardingViewModel.updateIsFirstRun(false)
            if (isFirstRun) {
                val intent = Intent(this, BoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        val route = intent.getStringExtra(BoardingActivity.ROUTE)

        route?.let { to ->
            when (to) {
                BoardingActivity.ROUTE_LOGIN -> {
                    navigateToLogin(navController)
                }
                BoardingActivity.ROUTE_REGISTER -> {
                    navigateToRegister(navController)
                }
            }
        }
    }


    private fun navigateToLogin(navController: NavController) {
        val directions = MainNavGraphDirections.actionToLoginNav()
        navController.navigate(directions)
    }

    private fun navigateToRegister(navController: NavController) {
        val directions = MainNavGraphDirections.actionToRegister()
        navController.navigate(directions)
    }
}