package com.simpleapp.challenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.simpleapp.challenge.data.model.User

@Dao
interface UserDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(user: User)

  @Query("SELECT * FROM Users WHERE username=:username")
  suspend fun findByUsername(username: String): User?

  @Query("SELECT * FROM Users WHERE username=:username AND Password=:password")
  fun getByUsernameAndPassword(username: String, password: String): User?

  @Insert
  fun insertAll(users: List<User>)

  @Query("SELECT * FROM Users")
  fun getAll(): List<User>

}