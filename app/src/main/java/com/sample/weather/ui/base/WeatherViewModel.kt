package com.sample.weather.ui.base

import androidx.lifecycle.ViewModel
import com.sample.weather.data.db.repository.ForecastRepository
import com.sample.weather.data.provider.UnitProvider
import com.sample.weather.internal.UnitSystem
import com.sample.weather.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getLocation()
    }
}