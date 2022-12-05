package com.capstone.ditalent.utils


sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val uiText: UiText) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
//
//inline fun <reified T> DocumentReference.snapshotsFlow(): Flow<Result<T?>> {
//    return callbackFlow {
//        val registration = addSnapshotListener { snapshot, exception ->
//            if (exception != null) {
//                cancel(message = "Error getting DocumentReference snapshot", cause = exception)
//            } else if (snapshot != null) {
//                trySendBlocking(Result.Success(
//                    snapshot.toObject<T>()
//                ))
//            }
//        }
//        awaitClose { registration.remove() }
//    }
//}
//
//inline fun <reified T : Any> Query.snapshotsFlow(): Flow<Result<List<T>>> {
//    return callbackFlow {
//        val registration = addSnapshotListener { snapshot, exception ->
//            if (exception != null) {
//                cancel(message = "Error getting DocumentReference snapshot", cause = exception)
//            } else if (snapshot != null) {
//                trySendBlocking(
//                    Result.Success(
//                        snapshot.toObjects()
//                    )
//                )
//            }
//        }
//        awaitClose { registration.remove() }
//    }
//}