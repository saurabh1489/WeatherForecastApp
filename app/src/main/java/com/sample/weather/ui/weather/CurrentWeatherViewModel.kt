package com.sample.weather.ui.weather

import androidx.lifecycle.ViewModel
import com.sample.weather.data.db.repository.ForecastRepository
import com.sample.weather.internal.UnitSystem
import com.sample.weather.internal.lazyDeferred

class CurrentWeatherViewModel(private val repository: ForecastRepository) : ViewModel() {
    val weather by lazyDeferred() {
        repository.getCurrentWeather(UnitSystem.METRIC)
    }

    val unit = UnitSystem.METRIC

    val isMetricUnit = unit == UnitSystem.METRIC
}