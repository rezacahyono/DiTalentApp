package com.capstone.ditalent.ui.talent.fragments.reward.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.FragmentRewardDetailBinding


class RewardDetailFragment : Fragment() {

    private var _binding: FragmentRewardDetailBinding? = null
    private val binding get() = _binding as FragmentRewardDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardDetailBinding.inflate(layoutInflater, container, false)
        binding.layoutToolbar.toolbar.apply {
            title = getString(R.string.reward)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
            setNavigationOnClickListener { navigateToBack() }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun navigateToBack(){
        findNavController().navigateUp()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}