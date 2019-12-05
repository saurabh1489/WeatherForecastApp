package com.sample.weather.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.sample.weather.di.AppContext

abstract class PreferenceProvider(@AppContext private val context: Context) {
    protected val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)
}