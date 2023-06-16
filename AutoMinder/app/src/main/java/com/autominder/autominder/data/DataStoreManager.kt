package com.autominder.autominder.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.autominder.autominder.data.database.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_DATA = "USER_DATA"
val Context.dataStore by preferencesDataStore(USER_DATA)

class DataStoreManager(val context: Context) {

    suspend fun saveUserData(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }


    fun getUserData(): Flow<String> = context.dataStore.data.map {
        it[TOKEN] ?: ""
    }

    suspend fun deleteUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        val TOKEN = stringPreferencesKey("token")
    }
}

