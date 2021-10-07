package com.simpleapp.challenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.simpleapp.challenge.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
  abstract fun userDao(): UserDao
}