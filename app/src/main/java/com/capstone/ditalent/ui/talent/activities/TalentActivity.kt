package com.capstone.ditalent.ui.talent.activities


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.ActivityTalentBinding
import com.capstone.ditalent.databinding.DialogBottomAboutAppBinding
import com.capstone.ditalent.ui.talent.fragments.home.HomeTalentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TalentActivity : AppCompatActivity() {
    private var _binding: ActivityTalentBinding? = null
    private val binding get() = _binding as ActivityTalentBinding

    private var _bindingBottomSheetAboutApp: DialogBottomAboutAppBinding? = null
    private val bindingBottomSheetAboutApp get() = _bindingBottomSheetAboutApp as DialogBottomAboutAppBinding


    private lateinit var navController: NavController

    private val homeTalentViewModel: HomeTalentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityTalentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_talent) as NavHostFragment
        navController = navHostFragment.navController

        homeTalentViewModel.isFirstRunHome.observe(this) { show ->
            if (show) {
                showDialogAboutApp()
            }
        }

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
                    bottomNav.isVisible = true
                }
                else -> {
                    bottomNav.isVisible = false
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
                homeTalentViewModel.updateIsFirstRunHome(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}