package com.capstone.ditalent.ui.umkm.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
            when {
                state.isError -> {
                    Toast.makeText(requireContext(), "Erro", Toast.LENGTH_SHORT).show()
                }
                state.isLoading -> {

                }
                else -> {
                    listTalentAdapter.submitList(state.talents)
                }
            }
        }

        setupViewHome()
    }

    private fun setupViewHome() {
        binding.apply {
            edtSearch.setOnClickListener { navigateToSearch(true) }
            tvCtaTalentMore.setOnClickListener { navigateToSearch(false) }
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

    private fun navigateToSearch(isFocus: Boolean) {
        val directions = HomeUmkmFragmentDirections.actionHomeUmkmNavToSearchUmkmNav(isFocus)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}