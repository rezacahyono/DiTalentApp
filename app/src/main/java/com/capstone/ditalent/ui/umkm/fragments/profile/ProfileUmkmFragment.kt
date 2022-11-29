package com.capstone.ditalent.ui.umkm.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.ditalent.databinding.FragmentProfileUmkmBinding


class ProfileUmkmFragment : Fragment() {

    private var _binding: FragmentProfileUmkmBinding? = null
    private val binding get() = _binding as FragmentProfileUmkmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileUmkmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}