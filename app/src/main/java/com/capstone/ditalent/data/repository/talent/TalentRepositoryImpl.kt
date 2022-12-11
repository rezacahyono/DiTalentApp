package com.capstone.ditalent.data.repository.talent

import com.capstone.ditalent.R
import com.capstone.ditalent.model.Role
import com.capstone.ditalent.model.User
import com.capstone.ditalent.ui.umkm.fragments.search.Filter
import com.capstone.ditalent.ui.umkm.fragments.search.Sort
import com.capstone.ditalent.utils.Constant.RATE
import com.capstone.ditalent.utils.Constant.ROLE
import com.capstone.ditalent.utils.Constant.USERS_COLLECTION
import com.capstone.ditalent.utils.Result
import com.capstone.ditalent.utils.UiText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TalentRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TalentRepository {

    override fun getTopUserRoleTalent(): Flow<Result<List<User>>> =
        db.collection(USERS_COLLECTION).whereEqualTo(ROLE, Role.TALENT.toString()).limit(5)
            .snapshots()
            .mapNotNull { snapshot ->
                Result.Success(snapshot.toObjects<User>()) as Result<List<User>>
            }
            .catch { emit(Result.Error(UiText.StringResource(R.string.text_message_error_something))) }
            .onStart { emit(Result.Loading) }
            .flowOn(ioDispatcher)

    override fun getAllUserRoleTalent(
        filter: Filter?
    ): Flow<Result<List<User>>> {
        val query = db.collection(USERS_COLLECTION).whereEqualTo(ROLE, Role.TALENT.toString())
        if (filter != null) {
            val sort = filter.sort
            sort?.let {
                when (it) {
                    Sort.RATE_HIGH -> query.orderBy(RATE, Query.Direction.DESCENDING)
                    Sort.RATE_LOW -> query.orderBy(RATE, Query.Direction.ASCENDING)
                    Sort.FOLLOWERS_HIGH -> {}
                    Sort.FOLLOWERS_LOW -> {}
                    Sort.NOW -> {}
                }
            }
        }

        return query
            .snapshots()
            .mapNotNull { snapshot ->
                Result.Success(snapshot.toObjects<User>()) as Result<List<User>>
            }
            .catch { emit(Result.Error(UiText.StringResource(R.string.text_message_error_something))) }
            .onStart { emit(Result.Loading) }
            .flowOn(ioDispatcher)
    }

}