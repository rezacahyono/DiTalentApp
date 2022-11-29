package com.capstone.ditalent.ui.umkm.fragments.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.ditalent.databinding.FragmentProjectUmkmBinding


class ProjectUmkmFragment : Fragment() {

    private var _binding: FragmentProjectUmkmBinding? = null
    private val binding get() = _binding as FragmentProjectUmkmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectUmkmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}