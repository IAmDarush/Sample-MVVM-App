package com.simpleapp.challenge.data.repository

import com.simpleapp.challenge.data.local.UserDao
import com.simpleapp.challenge.data.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val userDao: UserDao) : AuthRepository {

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

}