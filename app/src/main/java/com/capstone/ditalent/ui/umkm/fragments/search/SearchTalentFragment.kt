package com.capstone.ditalent.ui.umkm.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.ditalent.R
import com.capstone.ditalent.adapter.ListTalentAdapter
import com.capstone.ditalent.component.LoadingDialog
import com.capstone.ditalent.databinding.FragmentSearchTalentBinding
import com.capstone.ditalent.utils.Utilities.showSoftKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchTalentFragment : Fragment() {

    private var _binding: FragmentSearchTalentBinding? = null
    private val binding get() = _binding as FragmentSearchTalentBinding

    private lateinit var listTalentAdapter: ListTalentAdapter
    private lateinit var loadingDialog: LoadingDialog

    private val args: SearchTalentFragmentArgs by navArgs()

    private val searchUmkmViewModel: SearchTalentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchTalentBinding.inflate(layoutInflater, container, false)
        binding.layoutToolbar.toolbar.navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutToolbar.toolbar.setNavigationOnClickListener {
            navigateToBack()
        }

        loadingDialog = LoadingDialog(requireContext())

        listTalentAdapter = ListTalentAdapter {  }

        setupSearchView()
        setupFilterMenu()

        searchUmkmViewModel.searchTalentState.observe(viewLifecycleOwner) { state ->
            when {
                state.isError -> {
                    loadingDialog.hideDialog()
                }
                state.isLoading -> {
                    loadingDialog.showDialog()
                }
                else -> {
                    loadingDialog.hideDialog()
                    listTalentAdapter.submitList(state.talents)
                }
            }
        }

        setupRecyclerTalents()
    }

    private fun setupRecyclerTalents() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerTalents.apply {
            layoutManager = gridLayoutManager
            adapter = listTalentAdapter
        }
    }

    private fun setupSearchView() {
        val searchView = binding.layoutToolbar.searchView

        val searchQueryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchUmkmViewModel.getTalentsByName(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }

        searchView.apply {
            if (args.isFocus) {
                queryHint = getString(R.string.search_talent)
            }
            requestFocus()
            setOnQueryTextFocusChangeListener { view, isFocuses ->
                if (isFocuses) {
                    showSoftKeyboard(requireContext(), view.findFocus())
                }
            }

            setOnQueryTextListener(searchQueryTextListener)
        }
    }

    private fun setupFilterMenu() {
        val menuFilter = binding.layoutToolbar.toolbar.menu.getItem(0)
        menuFilter.setOnMenuItemClickListener {
            navigateToDialogFiterUmkm()
            true
        }
    }

    private fun navigateToBack() {
        findNavController().navigateUp()
    }

    private fun navigateToDialogFiterUmkm() {
        val directions = SearchTalentFragmentDirections.actionSearchTalentNavToDialogFilterUmkmNav()
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}