package com.simpleapp.challenge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
  @PrimaryKey(autoGenerate = true) val userId: Int? = null,
  @ColumnInfo(name = "username") val username: String,
  @ColumnInfo(name = "password") val password: String,
  @ColumnInfo(name = "country") val country: String
) {

  companion object {

    fun create(username: String, password: String, country: String): User {
      return User(userId = null, username = username, password = password, country = country)
    }

  }

}