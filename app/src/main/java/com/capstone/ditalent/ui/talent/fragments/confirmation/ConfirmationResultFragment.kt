package com.capstone.ditalent.ui.talent.fragments.confirmation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.ditalent.R

class ConfirmationResultFragment : Fragment() {

    companion object {
        fun newInstance() = ConfirmationResultFragment()
    }

    private lateinit var viewModel: ConfirmationResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirmation_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfirmationResultViewModel::class.java)
        // TODO: Use the ViewModel
    }

}