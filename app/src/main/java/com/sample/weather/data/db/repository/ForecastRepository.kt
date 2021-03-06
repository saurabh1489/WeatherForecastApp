package com.sample.weather.data.db.repository

import androidx.lifecycle.LiveData
import com.sample.weather.data.db.entity.CurrentWeatherEntity
import com.sample.weather.data.db.entity.WeatherLocation
import com.sample.weather.internal.UnitSystem
import com.sample.weather.vi.Resource

interface ForecastRepository {
    suspend fun getCurrentWeather(unit: UnitSystem): LiveData<Resource<CurrentWeatherEntity>>
    suspend fun getLocation(): LiveData<WeatherLocation>
}