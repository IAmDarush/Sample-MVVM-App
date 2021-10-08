package com.simpleapp.challenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class SimpleApp : Application() {

  @Inject
  lateinit var timberTree: Timber.Tree

}