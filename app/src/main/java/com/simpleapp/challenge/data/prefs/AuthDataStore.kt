package com.simpleapp.challenge.data.prefs

interface AuthDataStore {
  suspend fun logInUser()
  suspend fun isUserLoggedIn(): Boolean
  suspend fun logOutUser()
}