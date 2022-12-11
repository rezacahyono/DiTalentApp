package com.capstone.ditalent.data.repository.talent

import com.capstone.ditalent.model.User
import com.capstone.ditalent.ui.umkm.fragments.search.Filter
import kotlinx.coroutines.flow.Flow
import com.capstone.ditalent.utils.Result

interface TalentRepository {

    fun getTopUserRoleTalent(): Flow<Result<List<User>>>

    fun getAllUserRoleTalent(
        filter: Filter? = null
    ): Flow<Result<List<User>>>

}