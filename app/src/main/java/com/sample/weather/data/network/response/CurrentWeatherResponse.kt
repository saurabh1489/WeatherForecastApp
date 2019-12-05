package com.sample.weather.data.network.response


import com.google.gson.annotations.SerializedName
import com.sample.weather.data.db.entity.CurrentWeatherEntity
import com.sample.weather.data.db.entity.WeatherLocation

data class CurrentWeatherResponse(
    @SerializedName("current")
    val current: CurrentWeatherEntity,
    @SerializedName("location")
    val location: WeatherLocation
)