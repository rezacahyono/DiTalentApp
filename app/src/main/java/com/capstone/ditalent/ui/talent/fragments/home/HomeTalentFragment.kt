package com.capstone.ditalent.ui.talent.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.ditalent.R
import com.capstone.ditalent.adapter.ListRewardAdapter
import com.capstone.ditalent.databinding.FragmentHomeTalentBinding
import com.capstone.ditalent.utils.DataDummy
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeTalentFragment : Fragment() {

    private var _binding: FragmentHomeTalentBinding? = null
    private val binding get() = _binding as FragmentHomeTalentBinding

    private val homeTalentViewModel: HomeTalentViewModel by viewModels()

    private lateinit var listRewardAdapter: ListRewardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeTalentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeTalentViewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.tvUsername.text = getString(R.string.greeting_user, user.name)
        }

        binding.apply {
            tvCtaRewardMore.setOnClickListener { navigateToReward() }
            tvCtaProjectMore.setOnClickListener { navigateToProject() }
        }

        setupRecyclerReward()
    }

    private fun setupRecyclerReward() {
        listRewardAdapter = ListRewardAdapter { }
        val listReward = DataDummy.listReward
        listRewardAdapter.submitList(listReward)

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvReward.apply {
            layoutManager = linearLayoutManager
            adapter = listRewardAdapter
        }
    }

    private fun navigateToReward() {
        val directions = HomeTalentFragmentDirections.actionHomeTalentNavToRewardTalentNav()
        findNavController().navigate(directions)
    }

    private fun navigateToProject() {
        val directions = HomeTalentFragmentDirections.actionHomeTalentNavToProjectTalentNav()
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}