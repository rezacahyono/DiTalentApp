package com.capstone.ditalent.ui.talent.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.capstone.ditalent.R
import com.capstone.ditalent.data.clearUser
import com.capstone.ditalent.databinding.ActivityTalentBinding
import com.capstone.ditalent.ui.auth.activities.AuthActivity
import com.capstone.ditalent.ui.auth.fragments.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TalentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTalentBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTalentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogout.setOnClickListener {
            loginViewModel.setUserPref(clearUser())
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}