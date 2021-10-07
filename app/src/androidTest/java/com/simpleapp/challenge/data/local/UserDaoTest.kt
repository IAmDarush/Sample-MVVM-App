package com.simpleapp.challenge.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simpleapp.challenge.data.model.User
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

  private lateinit var userDao: UserDao
  private lateinit var db: AppDatabase

  @Before
  fun createDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    userDao = db.userDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }

  @Test
  @Throws(Exception::class)
  fun insertAndGetUser() = runBlocking {
    val user = User(username = "john", password = "1234", country = "UK", userId = 1)
    userDao.insert(user)
    val byUsername = userDao.findByUsername("john")
    assertThat(byUsername, equalTo(user))
  }

  @Test
  fun getUserByUsernameAndPassword() = runBlocking {
    val user1 = User(username = "john", password = "1234", country = "UK", userId = 1)
    val user2 = User(username = "tom", password = "abcd", country = "France", userId = 2)
    userDao.insert(user1)
    userDao.insert(user2)

    val userByUsernameAndPassword =
      userDao.getByUsernameAndPassword(username = "john", password = "1234")

    assertThat(userByUsernameAndPassword, equalTo(user1))
  }

  @Test
  fun insertAndGetAllUsers() = runBlocking {
    val userList = listOf(
      User(username = "john", password = "1234", country = "UK", userId = 1),
      User(username = "tom", password = "abcd", country = "France", userId = 2)
    )

    userDao.insertAll(userList)

    val allUsers = userDao.getAll()

    assertEquals(allUsers.size, 2)
    assertThat(allUsers[0], equalTo(userList[0]))
    assertThat(allUsers[1], equalTo(userList[1]))
  }

}