package com.sample.weather.data.provider

import com.sample.weather.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}