package com.capstone.ditalent.ui.talent.fragments.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.ditalent.databinding.FragmentProjectTalentBinding

class ProjectTalentFragment : Fragment() {

    private var _binding: FragmentProjectTalentBinding? = null
    private val binding get() = _binding as FragmentProjectTalentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectTalentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}