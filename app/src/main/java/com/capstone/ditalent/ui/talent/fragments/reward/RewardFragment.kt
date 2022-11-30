package com.capstone.ditalent.ui.talent.fragments.reward

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.ditalent.R
import com.capstone.ditalent.adapter.ListRewardAdapter
import com.capstone.ditalent.databinding.FragmentRewardBinding
import com.capstone.ditalent.utils.DataDummy


class RewardFragment : Fragment() {

    private var _binding: FragmentRewardBinding? = null
    private val binding get() = _binding as FragmentRewardBinding

    private lateinit var listRewardAdapter: ListRewardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listRewardAdapter = ListRewardAdapter {
            navigateToDetail()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardBinding.inflate(layoutInflater, container, false)
        binding.layoutToolbar.toolbar.title = getString(R.string.reward)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Data reward dummy
        val data = DataDummy.listReward
        listRewardAdapter.submitList(data)

        setupRecyclerReward()
    }

    private fun setupRecyclerReward() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerReward.apply {
            layoutManager = linearLayoutManager
            adapter = listRewardAdapter
        }
    }

    private fun navigateToDetail(){
        val directions = RewardFragmentDirections.actionRewardTalentNavToRewardDetailNav()
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}