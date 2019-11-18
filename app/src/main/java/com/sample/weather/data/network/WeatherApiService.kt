package com.sample.weather.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import com.sample.weather.data.db.entity.CurrentWeatherEntity
import com.sample.weather.data.network.response.CurrentWeatherResponse
import com.sample.weather.internal.UnitSystem
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "e090b8a903ebf3dd26953abb441e7214"
const val BASE_URL = "http://api.weatherstack.com/"

interface WeatherApiService {

    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String,
        @Query("unit") unit: UnitSystem = UnitSystem.METRIC
    ): LiveData<CurrentWeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            enumConverterFactory: EnumConverterFactory
        ): WeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()
                val request = chain.request().newBuilder().url(url).build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor).build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(enumConverterFactory)
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}