package com.simpleapp.challenge.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import com.simpleapp.challenge.BuildConfig
import com.simpleapp.challenge.data.local.AppDatabase
import com.simpleapp.challenge.data.local.UserDao
import com.simpleapp.challenge.data.repository.AuthRepository
import com.simpleapp.challenge.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

  private const val DATABASE_NAME: String = "user-list.db"

  @Provides
  @Singleton
  fun provideApplicationTree(): Timber.Tree {
    return object : Timber.DebugTree() {
      override fun isLoggable(tag: String?, priority: Int) =
        BuildConfig.DEBUG || priority >= Log.INFO
    }.apply { Timber.plant(this) }
  }

  @Provides
  @Singleton
  fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
    return context.getSharedPreferences("SimpleAppPreferences", Context.MODE_PRIVATE)
  }

  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

  @Provides
  @Singleton
  fun provideUserDao(database: AppDatabase) = database.userDao()

  @Provides
  @Singleton
  fun provideAuthRepository(userDao: UserDao): AuthRepository {
    return AuthRepositoryImpl(userDao)
  }

}