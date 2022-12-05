package com.capstone.ditalent.ui.talent.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.ditalent.data.preferences.SettingPreferences
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.model.User
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeTalentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val settingPreferences: SettingPreferences,
) : ViewModel() {

    val isFirstRunHome: LiveData<Boolean> = settingPreferences.isFirstRunHome.asLiveData()

    val currentUser: LiveData<FirebaseUser> = userRepository.currentUser.asLiveData()

    fun getUser(userId: String): LiveData<User> = userRepository.getUser(userId).asLiveData()

    fun updateIsFirstRunHome(value: Boolean) {
        viewModelScope.launch {
            settingPreferences.updateIsFirstRunHome(value)
        }
    }

}