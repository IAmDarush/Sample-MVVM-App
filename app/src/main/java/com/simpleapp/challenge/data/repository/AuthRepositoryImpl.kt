package com.simpleapp.challenge.data.repository

import com.simpleapp.challenge.data.local.UserDao
import com.simpleapp.challenge.data.model.User
import com.simpleapp.challenge.data.prefs.AuthDataStore
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
  private val userDao: UserDao,
  private val authDataStore: AuthDataStore
) : AuthRepository {

  override suspend fun registerUser(user: User) {
    userDao.insert(user)
  }

  override suspend fun getAllUsers(): List<User> {
    return userDao.getAll()
  }

  override suspend fun getUserByUsername(username: String): User? {
    return userDao.findByUsername(username)
  }

  override fun loginUser(username: String, password: String): User? {
    return userDao.getByUsernameAndPassword(username, password)
  }

  override suspend fun logOutUser() {
    userDao.deleteAllUsers()
  }

  override suspend fun setUserLoggedIn() = authDataStore.logInUser()

  override suspend fun isUserLoggedIn() = authDataStore.isUserLoggedIn()

  override suspend fun setUserLoggedOut() = authDataStore.logOutUser()

}