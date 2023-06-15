package com.autominder.autominder.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.autominder.autominder.data.database.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_DATA_STORE = "USER_DATA_STORE"
val Context.dataStore by preferencesDataStore(USER_DATA_STORE)

class DataStoreManager(val context: Context) {

    suspend fun saveUserData(userModel: UserModel) {
        context.dataStore.edit { preferences ->
            preferences[ID] = userModel.userId.toString()
            preferences[EMAIL] = userModel.email
            preferences[USERNAME] = userModel.email
            preferences[ROLES] = userModel.roles
            preferences[TOKEN] = userModel.token
        }
    }

    fun getUserData(): Flow<UserModel> = context.dataStore.data.map {
        UserModel(
            userId = it[ID]?.toLong() ?: 0,
            email = it[EMAIL] ?: "",
            username = it[USERNAME] ?: "",
            roles = it[ROLES] ?: "",
            token = it[TOKEN] ?: ""
        )
    }

    suspend fun deleteUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        val ID = stringPreferencesKey("user_id")
        val EMAIL = stringPreferencesKey("email")
        val USERNAME = stringPreferencesKey("username")
        val ROLES = stringPreferencesKey("roles")
        val TOKEN = stringPreferencesKey("token")
    }
}

