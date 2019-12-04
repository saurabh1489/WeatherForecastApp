package com.sample.weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sample.weather.data.db.dao.CurrentWeatherDao
import com.sample.weather.data.db.dao.LocationDao
import com.sample.weather.data.db.entity.CurrentWeatherEntity
import com.sample.weather.data.db.entity.LocationEntity

@Database(
    entities = [CurrentWeatherEntity::class, LocationEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListToStringTypeConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun locationDao(): LocationDao
}