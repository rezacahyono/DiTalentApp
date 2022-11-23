package com.capstone.ditalent.ui.talent.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.FragmentProfileTalentBinding


class ProfileTalentFragment : Fragment() {

    private var _binding: FragmentProfileTalentBinding? = null
    private val binding get() = _binding as FragmentProfileTalentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileTalentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}