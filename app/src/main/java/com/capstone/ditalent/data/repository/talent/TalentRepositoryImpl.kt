package com.capstone.ditalent.data.repository.talent

import com.capstone.ditalent.R
import com.capstone.ditalent.model.Role
import com.capstone.ditalent.model.Talent
import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.Constant.ROLE
import com.capstone.ditalent.utils.Constant.USERS_COLLECTION
import com.capstone.ditalent.utils.Result
import com.capstone.ditalent.utils.UiText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
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

    override fun getUsersRoleTalent(): Flow<Result<List<Pair<User, Talent>>>> =
        db.collection(USERS_COLLECTION).whereEqualTo(ROLE, Role.TALENT.toString()).limit(5).snapshots()
            .mapNotNull<QuerySnapshot, List<User>> { snapshot ->
                snapshot.toObjects()
            }.zip(
                db.collectionGroup(Role.TALENT.toString()).limit(5).snapshots()
                    .mapNotNull<QuerySnapshot, List<Talent>> { it.toObjects() }.flowOn(ioDispatcher)
            ) { users, talents ->
                users.zip(talents)
            }.mapNotNull {
                Result.Success(it) as Result<List<Pair<User, Talent>>>
            }
            .catch { emit(Result.Error(UiText.StringResource(R.string.text_message_error_something))) }
            .onStart { emit(Result.Loading) }
            .flowOn(ioDispatcher)
}