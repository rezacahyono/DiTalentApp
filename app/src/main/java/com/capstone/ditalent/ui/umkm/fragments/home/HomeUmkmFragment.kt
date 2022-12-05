package com.capstone.ditalent.ui.umkm.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.capstone.ditalent.databinding.FragmentHomeUmkmBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeUmkmFragment : Fragment() {
    private var _binding: FragmentHomeUmkmBinding? = null
    private val binding get() = _binding as FragmentHomeUmkmBinding

    private val homeUmkmViewModel: HomeUmkmViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeUmkmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeUmkmViewModel.homeUmkmUiState.observe(viewLifecycleOwner) { state ->
            if (!state.isLoading && !state.isError) {
                binding.text.text = state.talents.size.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}