package com.simpleapp.challenge.di

import android.util.Log
import com.simpleapp.challenge.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

  @Provides
  @Singleton
  fun provideApplicationTree(): Timber.Tree {
    return object : Timber.DebugTree() {
      override fun isLoggable(tag: String?, priority: Int) =
        BuildConfig.DEBUG || priority >= Log.INFO
    }.apply { Timber.plant(this) }
  }

}