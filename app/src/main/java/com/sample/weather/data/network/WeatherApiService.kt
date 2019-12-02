package com.sample.weather.data.network

import androidx.lifecycle.LiveData
import com.sample.weather.data.network.response.CurrentWeatherResponse
import com.sample.weather.internal.UnitSystem
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String,
        @Query("unit") unit: UnitSystem = UnitSystem.METRIC
    ): LiveData<CurrentWeatherResponse>
}