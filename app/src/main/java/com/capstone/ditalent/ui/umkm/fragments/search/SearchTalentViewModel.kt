package com.capstone.ditalent.ui.umkm.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ditalent.data.repository.talent.TalentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.capstone.ditalent.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTalentViewModel @Inject constructor(
    private val talentRepository: TalentRepository
) : ViewModel() {

    private val _searchTalentState: MutableLiveData<SearchTalentUiState> = MutableLiveData()
    val searchTalentState: LiveData<SearchTalentUiState> = _searchTalentState

    private val _filter: MutableLiveData<Filter> = MutableLiveData()
    val filter: LiveData<Filter> = _filter

    fun getTalentsByName(name: String) {
        viewModelScope.launch {
            talentRepository.getAllUserRoleTalent(filter.value).collect { result ->
                when (result) {
                    is Result.Success -> _searchTalentState.value =
                        SearchTalentUiState(talents = result.data.filter {
                            it.name?.contains(name,true) ?: false
                        })
                    is Result.Loading -> _searchTalentState.value =
                        SearchTalentUiState(isLoading = true)
                    is Result.Error -> _searchTalentState.value =
                        SearchTalentUiState(isError = true)
                }
            }
        }
    }

    fun setFilter(filter: Filter) {
        _filter.value = filter
    }

    fun clearFilter() {
        _filter.value = Filter()
    }
}