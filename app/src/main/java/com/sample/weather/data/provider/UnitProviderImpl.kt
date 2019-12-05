package com.sample.weather.data.provider

import android.content.Context
import com.sample.weather.di.AppContext
import com.sample.weather.internal.UnitSystem
import javax.inject.Inject

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl @Inject constructor(@AppContext val context: Context) :
    PreferenceProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}