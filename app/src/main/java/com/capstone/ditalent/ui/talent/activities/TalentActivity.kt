package com.capstone.ditalent.ui.talent.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.ActivityTalentBinding
import com.capstone.ditalent.utils.Utilities.margin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TalentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTalentBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTalentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_talent) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNavigation(navController)
        setupBottomNavVisible(navController)

    }

    private fun setupBottomNavigation(navController: NavController) {
        val bottomNav = binding.bottomNavTalent
        bottomNav.setupWithNavController(navController)
    }

    private fun setupBottomNavVisible(navController: NavController) {
        val bottomNav = binding.bottomNavTalent
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_talent_nav,
                R.id.project_talent_nav,
                R.id.reward_talent_nav,
                R.id.profile_talent_nav -> {
                    binding.fragmentContainerTalent.margin(bottom = 56f)
                    bottomNav.isVisible = true
                }
                else -> {
                    binding.fragmentContainerTalent.margin(bottom = 0f)
                    bottomNav.isVisible = false
                }
            }
        }

//        bottomNav.setOnItemReselectedListener {
//            Log.d("TAG", "item : ${it.itemId}")
//            Log.d("TAG", "backStack: ${navController.currentDestination?.label}")
////            if (navController.currentBackStackEntry != null &&
////                navController.currentDestination?.id != R.id.home_talent_nav
////            )
////                navController.popBackStack()
//        }
    }
}