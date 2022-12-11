package com.capstone.ditalent.ui.umkm.fragments.search.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.ditalent.R
import com.capstone.ditalent.databinding.DialogFilterBinding
import com.capstone.ditalent.ui.umkm.fragments.search.Filter
import com.capstone.ditalent.ui.umkm.fragments.search.SearchTalentViewModel
import com.capstone.ditalent.ui.umkm.fragments.search.Sort
import com.capstone.ditalent.utils.Utilities.getFormatedNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogFilterUmkm : DialogFragment() {

    private var _binding: DialogFilterBinding? = null
    private val binding get() = _binding as DialogFilterBinding

    private val searchTalentViewModel: SearchTalentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Widget_DiTalent_FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFilterBinding.inflate(layoutInflater, container, false)
        binding.layoutToolbar.toolbar.title = getString(R.string.filter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutToolbar.toolbar.setNavigationOnClickListener {
            navigateToBack()
        }

        setupFilter()

        searchTalentViewModel.filter.observe(viewLifecycleOwner){
            Log.d("TAG", "onViewCreated: ${it.sort}")
        }
    }


    private fun setupFilter() {
        val filter = Filter()
        val rangeRate = binding.rsFollowers
        rangeRate.setLabelFormatter { value: Float ->
            getFormatedNumber(value.toLong())
        }

        val clearMenu = binding.layoutToolbar.toolbar.menu.getItem(0)

        searchTalentViewModel.filter.observe(viewLifecycleOwner) {
            clearMenu.isEnabled = it.sort != null
        }

        clearMenu.setOnMenuItemClickListener {
            searchTalentViewModel.clearFilter()
            true
        }

        binding.btnApply.setOnClickListener {
            applyFilter(filter)
        }
    }

    private fun applyFilter(filter: Filter) {
        binding.cgSort.setOnCheckedStateChangeListener { _, checkedIds ->
            when (checkedIds.first()) {
                R.id.c_sort_by_high_rate -> filter.sort = Sort.RATE_HIGH
                R.id.c_sort_by_low_rate -> filter.sort = Sort.RATE_LOW
                R.id.c_sort_by_new -> filter.sort = Sort.NOW
                R.id.c_sort_by_high_followers -> filter.sort = Sort.FOLLOWERS_HIGH
                R.id.c_sort_by_low_followers -> filter.sort = Sort.FOLLOWERS_LOW
            }
        }

        searchTalentViewModel.setFilter(filter)
    }

    private fun navigateToBack() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}