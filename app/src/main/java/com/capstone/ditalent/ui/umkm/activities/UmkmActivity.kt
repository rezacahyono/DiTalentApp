package com.capstone.ditalent.ui.umkm.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.capstone.ditalent.data.clearUser
import com.capstone.ditalent.databinding.ActivityUmkmBinding
import com.capstone.ditalent.ui.auth.fragments.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UmkmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUmkmBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUmkmBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnLogout.setOnClickListener {
//            loginViewModel.setUserPref(clearUser())
//            Navigation.findNavController(binding.root).navigateUp()
//        }
    }
}