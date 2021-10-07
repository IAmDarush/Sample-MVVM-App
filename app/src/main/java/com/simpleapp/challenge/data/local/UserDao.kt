package com.simpleapp.challenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.simpleapp.challenge.data.model.User

@Dao
interface UserDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addUser(user: User)

  @Query("SELECT * FROM Users WHERE username=:username")
  suspend fun getUserByUsername(username: String): User?

}