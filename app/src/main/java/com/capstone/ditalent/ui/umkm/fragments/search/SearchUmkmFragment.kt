package com.capstone.ditalent.ui.umkm.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.FragmentSearchUmkmBinding


class SearchUmkmFragment : Fragment() {

    private var _binding: FragmentSearchUmkmBinding? = null
    private val binding get() = _binding as FragmentSearchUmkmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchUmkmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}