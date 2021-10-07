package com.simpleapp.challenge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
  @PrimaryKey(autoGenerate = true) val userId: Int = 1,
  @ColumnInfo(name = "username") val username: String,
  @ColumnInfo(name = "password") val password: String,
  @ColumnInfo(name = "country") val country: String
)