package com.capstone.ditalent.ui.umkm.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.FragmentHomeUmkmBinding

class HomeUmkmFragment : Fragment() {
    private var _binding: FragmentHomeUmkmBinding? = null
    private val binding get() = _binding as FragmentHomeUmkmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeUmkmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}