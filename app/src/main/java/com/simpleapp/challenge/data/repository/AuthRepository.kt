package com.simpleapp.challenge.data.repository

import com.simpleapp.challenge.data.model.User

interface AuthRepository {
  suspend fun registerUser(user: User)
  fun loginUser(username: String, password: String): User?
  suspend fun getAllUsers(): List<User>
  suspend fun getUserByUsername(username: String): User?
  suspend fun logOutUser()
  suspend fun setUserLoggedIn()
  suspend fun isUserLoggedIn(): Boolean
  suspend fun setUserLoggedOut()
}