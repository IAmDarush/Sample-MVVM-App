package com.simpleapp.challenge.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class PreferencesManagerImpl @Inject constructor(
  private val sharedPrefs: SharedPreferences
) : PreferencesManager {


  override var token: String?
    get() = sharedPrefs.getString(PREF_TOKEN, null)
    set(value) {
      sharedPrefs.edit { putString(PREF_TOKEN, value) }
    }


  companion object {
    private const val PREF_TOKEN = "pref_token"
  }

}