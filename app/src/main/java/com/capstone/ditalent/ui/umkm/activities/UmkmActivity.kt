package com.capstone.ditalent.ui.umkm.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.ActivityUmkmBinding
import com.capstone.ditalent.utils.Utilities.margin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UmkmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUmkmBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUmkmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_umkm) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNavigation(navController)
        setupBottomNavVisible(navController)

    }

    private fun setupBottomNavigation(navController: NavController) {
        val bottomNav = binding.bottomNavUmkm
        bottomNav.setupWithNavController(navController)
    }

    private fun setupBottomNavVisible(navController: NavController) {
        val bottomNav = binding.bottomNavUmkm
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_umkm_nav,
                R.id.project_umkm_nav,
                R.id.chart_umkm_nav,
                R.id.profile_umkm_nav -> {
                    binding.fragmentContainerUmkm.margin(bottom = 56f)
                    bottomNav.isVisible = true
                }
                else -> {
                    binding.fragmentContainerUmkm.margin(bottom = 0f)
                    bottomNav.isVisible = false
                }
            }
        }
    }
}