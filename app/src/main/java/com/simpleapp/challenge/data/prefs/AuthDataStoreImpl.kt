package com.simpleapp.challenge.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

const val AUTH_DATASTORE_NAME = "auth_data_store"
const val AUTH_DATASTORE_KEY_IS_USER_LOGGED_IN = "is_user_logged_in"

class AuthDataStoreImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : AuthDataStore {

  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_DATASTORE_NAME)

  /**
   * Marks the user as logged in.
   */
  override suspend fun logInUser() {
    val dataStoreKey = booleanPreferencesKey(AUTH_DATASTORE_KEY_IS_USER_LOGGED_IN)
    context.dataStore.edit { authPrefs ->
      authPrefs[dataStoreKey] = true
    }
  }

  /**
   * Checks if the user is already logged in.
   */
  override suspend fun isUserLoggedIn(): Boolean {
    val dataStoreKey = booleanPreferencesKey(AUTH_DATASTORE_KEY_IS_USER_LOGGED_IN)
    val loginStatus = context.dataStore.data.first()

    return loginStatus[dataStoreKey]
           ?: false //If null return false else return the value in datastore
  }

  /**
   * Marks the user as logged out.
   */
  override suspend fun logOutUser() {
    val dataStoreKey = booleanPreferencesKey(AUTH_DATASTORE_KEY_IS_USER_LOGGED_IN)
    context.dataStore.edit { token ->
      token[dataStoreKey] = false
    }
  }
}