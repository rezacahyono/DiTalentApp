package com.capstone.ditalent.ui.umkm.fragments.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.FragmentAddProjectUmkmBinding

class AddProjectUmkmFragment : Fragment() {
    private var _binding: FragmentAddProjectUmkmBinding? = null
    private val binding get() = _binding as FragmentAddProjectUmkmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProjectUmkmBinding.inflate(layoutInflater, container, false)
        binding.layoutToolbar.toolbar.apply {
            title = getString(R.string.new_project)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutToolbar.toolbar.setNavigationOnClickListener { navigateToBack() }
    }

    private fun navigateToBack() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}