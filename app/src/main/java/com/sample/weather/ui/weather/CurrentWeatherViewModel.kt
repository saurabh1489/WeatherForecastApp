package com.sample.weather.ui.weather

import androidx.lifecycle.ViewModel
import com.sample.weather.data.db.repository.ForecastRepository
import com.sample.weather.data.provider.UnitProvider
import com.sample.weather.internal.UnitSystem
import com.sample.weather.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val repository: ForecastRepository,
    val unitProvider: UnitProvider
) : ViewModel() {
    val weather by lazyDeferred() {
        repository.getCurrentWeather(UnitSystem.METRIC)
    }

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit = unitSystem == UnitSystem.METRIC
}