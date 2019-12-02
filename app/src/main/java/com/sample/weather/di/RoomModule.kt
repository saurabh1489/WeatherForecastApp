package com.sample.weather.di

import android.content.Context
import androidx.room.Room
import com.sample.weather.data.db.WeatherDatabase
import com.sample.weather.data.db.dao.CurrentWeatherDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideDatabase(@AppContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            "Weather_Forecast.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCurrentWeatherDao(database: WeatherDatabase): CurrentWeatherDao {
        return database.currentWeatherDao()
    }
}