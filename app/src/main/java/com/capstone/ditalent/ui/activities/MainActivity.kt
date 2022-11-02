package com.capstone.ditalent.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.ditalent.databinding.ActivityMainBinding
import com.capstone.ditalent.ui.activities.boarding.BoardingActivity
import com.capstone.ditalent.ui.activities.boarding.BoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val boardingViewModel: BoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardingViewModel.isFirstRun.observe(this){ isFirstRun ->
            boardingViewModel.updateIsFirstRun(false)
            if (isFirstRun){
                val intent = Intent(this, BoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}