package com.sample.weather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.weather.data.db.repository.ForecastRepository

class CurrentWeatherViewModelFactory(private val repository: ForecastRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(repository) as T
    }
}