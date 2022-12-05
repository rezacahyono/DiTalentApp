package com.capstone.ditalent.data.repository.auth


import com.capstone.ditalent.R
import com.capstone.ditalent.model.Role
import com.capstone.ditalent.model.Talent
import com.capstone.ditalent.model.Umkm
import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.Constant.USERS_COLLECTION
import com.capstone.ditalent.utils.Result
import com.capstone.ditalent.utils.UiText
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val db: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserRepository {

    override val currentUser: Flow<FirebaseUser> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            it.currentUser?.let { user ->
                trySend(user)
            }
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.flowOn(ioDispatcher)


    override suspend fun checkUserIsExists(userId: String): Boolean {
        return db.collection(USERS_COLLECTION).document(userId).get().await().exists()
    }


    override fun getUser(userId: String): Flow<User> =
        db.collection(USERS_COLLECTION).document(userId).snapshots()
            .mapNotNull { it.toObject<User>() }.flowOn(ioDispatcher)


    override fun addUser(firebaseUser: FirebaseUser, role: String): Flow<Result<UiText>> =
        flow<Result<UiText>> {
            val userCollection = db.collection(USERS_COLLECTION)

            val roleData: Any = if (role == Role.TALENT.toString()) {
                Talent()
            } else {
                Umkm()
            }

            val userData = User(
                id = firebaseUser.uid,
                email = firebaseUser.email,
                photo = firebaseUser.photoUrl.toString(),
                name = firebaseUser.displayName,
                role = role
            )

            userCollection.document(firebaseUser.uid).set(userData).await()

            userCollection.document(firebaseUser.uid).collection(role).document(firebaseUser.uid)
                .set(roleData).await()

            emit(Result.Success(UiText.StringResource(R.string.text_result_register_success)))
        }.onStart { emit(Result.Loading) }
            .catch { emit(Result.Error(UiText.StringResource(R.string.text_result_register_failed))) }
            .flowOn(ioDispatcher)


    override fun loginWithEmailPassword(
        email: String, password: String
    ): Flow<Result<UiText>> = flow {

        val result = auth.signInWithEmailAndPassword(email, password).await()

        result.user?.let {

            emit(Result.Success(UiText.StringResource(R.string.text_result_login_success)))

        } ?: emit(Result.Error(UiText.StringResource(R.string.text_result_login_failed)))

    }.onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(UiText.StringResource(R.string.text_result_login_failed))) }
        .flowOn(ioDispatcher)


    override fun loginWithGoogle(credential: AuthCredential): Flow<Result<UiText>> =
        flow {

            val result = auth.signInWithCredential(credential).await()

            result.user?.let {

                emit(Result.Success(UiText.StringResource(R.string.text_result_register_success)))
            }
                ?: emit(Result.Error(UiText.StringResource(R.string.text_result_register_failed)))

        }.onStart { emit(Result.Loading) }
            .catch { emit(Result.Error(UiText.StringResource(R.string.text_result_register_failed))) }
            .flowOn(ioDispatcher)


    override fun register(
        name: String, email: String, role: String, noPhone: String, password: String
    ): Flow<Result<UiText>> = flow {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        result.user?.let { user ->

            val userCollectionReference = db.collection(USERS_COLLECTION)
            val profileUpdate = userProfileChangeRequest {
                displayName = name
            }

            user.updateProfile(profileUpdate).await()

            val roleData: Any = if (role == Role.TALENT.toString()) {
                Talent()
            } else {
                Umkm()
            }

            val userData = User(
                id = user.uid,
                email = email,
                photo = user.photoUrl.toString(),
                name = name,
                noPhone = noPhone,
                role = role
            )

            userCollectionReference.document(user.uid).set(userData).await()
            userCollectionReference.document(user.uid).collection(role).document(user.uid)
                .set(roleData).await()

            emit(Result.Success(UiText.StringResource(R.string.text_result_register_success)))
        } ?: emit(Result.Error(UiText.StringResource(R.string.text_result_register_failed)))

    }.onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(UiText.StringResource(R.string.text_result_register_failed))) }
        .flowOn(ioDispatcher)


    override fun logout(): Flow<Result<UiText>> = flow<Result<UiText>> {

        auth.signOut()
        googleSignInClient.signOut().await()
        emit(Result.Success(UiText.StringResource(R.string.text_result_logout_success)))

    }.onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(UiText.StringResource(R.string.text_result_logout_failed))) }
        .flowOn(ioDispatcher)


}