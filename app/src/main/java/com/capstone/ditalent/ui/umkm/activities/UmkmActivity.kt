package com.capstone.ditalent.ui.umkm.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.ActivityUmkmBinding
import com.capstone.ditalent.databinding.DialogBottomAboutAppBinding
import com.capstone.ditalent.ui.umkm.fragments.home.HomeUmkmViewModel
import com.capstone.ditalent.utils.Utilities.margin
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UmkmActivity : AppCompatActivity() {
    private var _binding: ActivityUmkmBinding? = null
    private val binding get() = _binding as ActivityUmkmBinding

    private var _bindingBottomSheetAboutApp: DialogBottomAboutAppBinding? = null
    private val bindingBottomSheetAboutApp get() = _bindingBottomSheetAboutApp as DialogBottomAboutAppBinding

    private lateinit var navController: NavController

    private val homeUmkmViewModel: HomeUmkmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityUmkmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_umkm) as NavHostFragment
        navController = navHostFragment.navController

        homeUmkmViewModel.isFirstRunHome.observe(this) { show ->
            if (show) {
                showDialogAboutApp()
            }
        }

        setupBottomNavigation(navController)
        setupBottomNavVisible(navController)

        binding.fabNewProject.setOnClickListener {
            navigateToAddProject(navController)
        }

    }

    private fun setupBottomNavigation(navController: NavController) {
        val bottomNav = binding.bottomNavUmkm
        bottomNav.setupWithNavController(navController)
    }

    private fun setupBottomNavVisible(navController: NavController) {
        val bottomNav = binding.bottomNavUmkm
        val fabNewProject = binding.fabNewProject
        val bottomAppBar = binding.bottomAppBar
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_umkm_nav,
                R.id.project_umkm_nav,
                R.id.chart_umkm_nav,
                R.id.profile_umkm_nav -> {
                    binding.fragmentContainerUmkm.margin(bottom = 56f)
                    bottomNav.isVisible = true
                    bottomAppBar.isVisible = true
                    fabNewProject.show()
                }
                else -> {
                    binding.fragmentContainerUmkm.margin(bottom = 0f)
                    bottomNav.isVisible = false
                    bottomAppBar.isVisible = false
                    fabNewProject.hide()
                }
            }
        }
    }

    private fun showDialogAboutApp() {
        _bindingBottomSheetAboutApp = DialogBottomAboutAppBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bindingBottomSheetAboutApp.root)

        dialog.apply {
            show()
            setOnDismissListener {
                homeUmkmViewModel.updateIsFirstRunHome(false)
            }
        }
    }

    private fun navigateToAddProject(navController: NavController) {
        navController.navigate(R.id.add_project_umkm_nav)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}