package com.capstone.ditalent.data.repository.auth


import com.capstone.ditalent.R
import com.capstone.ditalent.model.Role
import com.capstone.ditalent.model.Talent
import com.capstone.ditalent.model.Umkm
import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.Constant.USERS_COLLECTION
import com.capstone.ditalent.utils.UiText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserRepository {

    override val firebaseUser: Flow<FirebaseUser> = callbackFlow {
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

    override fun getUser(userId: String): Flow<User> =
        db.collection(USERS_COLLECTION).document(userId).snapshots()
            .mapNotNull {
                it.toObject(User::class.java)
            }


    override fun loginWithEmailPassword(
        email: String,
        password: String
    ): Flow<Map<Boolean, UiText>> = flow {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user?.let {
            emit(mapOf(true to UiText.StringResource(R.string.text_result_login_success)))
        } ?: emit(mapOf(false to UiText.StringResource(R.string.text_result_login_failed)))
    }.catch {
        emit(mapOf(false to UiText.StringResource(R.string.text_result_login_failed)))
    }.flowOn(ioDispatcher)

    override fun loginWithGoogle(
        credential: AuthCredential,
        role: String
    ): Flow<Map<Boolean, UiText>> = flow {
        val result = auth.signInWithCredential(credential).await()
        result.user?.let { user ->
            val userCollectionReference = db.collection(USERS_COLLECTION)

            val roleData: Any = if (role == Role.TALENT.toString()) {
                Talent()
            } else {
                Umkm()
            }

            val userData = User(
                id = user.uid,
                email = user.email,
                photo = user.photoUrl.toString(),
                name = user.displayName,
                role = role
            )
            userCollectionReference
                .document(user.uid).set(userData).await()

            val roleCollectionReference = userCollectionReference.document(user.uid)
            if (!roleCollectionReference.get().result.exists()) {
                roleCollectionReference.collection(role).document(user.uid).set(roleData).await()
            }
            emit(mapOf(true to UiText.StringResource(R.string.text_result_login_success)))
        } ?: emit(mapOf(false to UiText.StringResource(R.string.text_result_login_failed)))
    }.catch {
        emit(mapOf(false to UiText.StringResource(R.string.text_result_login_failed)))
    }.flowOn(ioDispatcher)

    override fun register(
        name: String,
        email: String,
        role: String,
        noPhone: String,
        password: String
    ): Flow<Map<Boolean, UiText>> = flow {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        result.user?.let { user ->
            val userCollectionReference = db.collection(USERS_COLLECTION)
            val profileUpdate = userProfileChangeRequest {
                displayName = name
            }
            user.updateProfile(profileUpdate)

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
            userCollectionReference
                .document(user.uid).set(userData).await()

            val roleCollectionReference = userCollectionReference.document(user.uid)
            roleCollectionReference.collection(role).document(user.uid).set(roleData).await()

            emit(mapOf(true to UiText.StringResource(R.string.text_result_register_success)))
        } ?: emit(mapOf(false to UiText.StringResource(R.string.text_result_register_failed)))
    }.catch {
        emit(mapOf(false to UiText.StringResource(R.string.text_result_register_failed)))
    }.flowOn(ioDispatcher)

    override fun logout(): Flow<Map<Boolean, UiText>> = flow<Map<Boolean, UiText>> {
        auth.signOut()
        emit(mapOf(true to UiText.StringResource(R.string.text_result_register_success)))
    }.catch {
        emit(mapOf(false to UiText.StringResource(R.string.text_result_register_failed)))
    }.flowOn(ioDispatcher)


}