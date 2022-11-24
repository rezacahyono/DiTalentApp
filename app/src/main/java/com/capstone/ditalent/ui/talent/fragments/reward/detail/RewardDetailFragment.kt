package com.capstone.ditalent.ui.talent.fragments.reward.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.ditalent.R

class RewardDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RewardDetailFragment()
    }

    private lateinit var viewModel: RewardDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reward_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RewardDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}