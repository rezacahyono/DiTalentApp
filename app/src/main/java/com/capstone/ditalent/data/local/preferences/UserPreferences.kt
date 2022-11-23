package com.capstone.ditalent.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.capstone.ditalent.data.clearUser
import com.capstone.ditalent.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserPreferences @Inject constructor(
    private val userPreference: DataStore<User>,
) {

    val userPref: Flow<User> = userPreference.data


    suspend fun setUserPref(user: User) {
        userPreference.updateData {
            user
        }
    }

    companion object UserSerializer : Serializer<User> {
        override val defaultValue: User
            get() = clearUser()

        override suspend fun readFrom(input: InputStream): User =
            withContext(Dispatchers.IO) {
                try {
                    Json.decodeFromString(
                        deserializer = User.serializer(),
                        string = input.readBytes().decodeToString()
                    )
                } catch (e: SerializationException) {
                    e.printStackTrace()
                    defaultValue
                }
            }


        override suspend fun writeTo(t: User, output: OutputStream) {
            withContext(Dispatchers.IO) {
                output.write(
                    Json.encodeToString(
                        serializer = User.serializer(), value = t
                    ).encodeToByteArray()
                )
            }
        }

    }
}