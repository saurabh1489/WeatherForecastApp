package com.sample.weather.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.sample.weather.di.AppContext
import com.sample.weather.internal.UnitSystem
import javax.inject.Inject

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl @Inject constructor(@AppContext val context: Context) : UnitProvider {

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}