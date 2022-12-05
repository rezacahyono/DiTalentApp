package com.capstone.ditalent.data.repository.talent

import com.capstone.ditalent.model.Talent
import com.capstone.ditalent.model.User
import kotlinx.coroutines.flow.Flow
import com.capstone.ditalent.utils.Result

interface TalentRepository {

    fun getUsersRoleTalent(): Flow<Result<List<Pair<User, Talent>>>>

}