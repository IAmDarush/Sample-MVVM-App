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
    val user = User(username = "john", password = "1234", country = "UK")
    userDao.addUser(user)
    val byUsername = userDao.getUserByUsername("john")
    assertThat(byUsername, equalTo(user))
  }

  @Test
  fun getUserByUsernameAndPassword() = runBlocking {
    val user1 = User(username = "john", password = "1234", country = "UK")
    val user2 = User(username = "tom", password = "abcd", country = "France")
    userDao.addUser(user1)
    userDao.addUser(user2)

    val userByUsernameAndPassword =
      userDao.getUserByUsernameAndPassword(username = "tom", password = "abcd")

    assertThat(userByUsernameAndPassword, equalTo(user2))
  }

}