package com.capstone.ditalent.ui.talent.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.model.User
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeTalentViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

//    val user: LiveData<User> by lazy {  userRepository.getUser.asLiveData()}

}