package com.sample.weather.ui.weather

import com.sample.weather.data.db.repository.ForecastRepository
import com.sample.weather.data.provider.UnitProvider
import com.sample.weather.internal.UnitSystem
import com.sample.weather.internal.lazyDeferred
import com.sample.weather.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val repository: ForecastRepository,
    val unitProvider: UnitProvider
) : WeatherViewModel(repository, unitProvider) {
    val weather by lazyDeferred() {
        repository.getCurrentWeather(UnitSystem.METRIC)
    }
}