package com.capstone.ditalent.ui.umkm.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.ditalent.R
import com.capstone.ditalent.adapter.ListTalentAdapter
import com.capstone.ditalent.databinding.FragmentHomeUmkmBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeUmkmFragment : Fragment() {
    private var _binding: FragmentHomeUmkmBinding? = null
    private val binding get() = _binding as FragmentHomeUmkmBinding

    private val homeUmkmViewModel: HomeUmkmViewModel by viewModels()

    private lateinit var listTalentAdapter: ListTalentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listTalentAdapter = ListTalentAdapter {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeUmkmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeUmkmViewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.tvUsername.text = getString(R.string.greeting_user, user.name)
        }

        homeUmkmViewModel.homeUmkmUiState.observe(viewLifecycleOwner) { state ->
            if (!state.isLoading && !state.isError) {
                listTalentAdapter.submitList(state.talents)
            }
        }

        setupRecyclerTopTalents()
    }

    private fun setupRecyclerTopTalents() {
        binding.recyclerTopTalents.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = listTalentAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}