package com.sample.weather.di

import android.app.Application
import android.content.Context
import android.util.Log
import com.sample.weather.ForecastApplication
import com.sample.weather.data.network.ConnectivityInterceptor
import com.sample.weather.data.network.EnumConverterFactory
import com.sample.weather.data.network.LiveDataCallAdapterFactory
import com.sample.weather.data.network.WeatherApiService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val API_KEY = "e090b8a903ebf3dd26953abb441e7214"
const val BASE_URL = "http://api.weatherstack.com/"

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherApiService(okHttpClient: OkHttpClient): WeatherApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(EnumConverterFactory())
            .build()
            .create(WeatherApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(connectivityInterceptor: ConnectivityInterceptor): OkHttpClient {
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("access_key", API_KEY)
                .build()
            val request = chain.request().newBuilder().url(url).build()
            return@Interceptor chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(connectivityInterceptor)
            .build();
    }

    @Provides
    @AppContext
    fun provideContext(application: Application): Context {
        return application
    }
}