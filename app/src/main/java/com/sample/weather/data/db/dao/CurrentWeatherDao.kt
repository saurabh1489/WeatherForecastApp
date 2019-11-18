package com.sample.weather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.weather.data.db.entity.CURRENT_WEATHER_ID
import com.sample.weather.data.db.entity.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAndInsert(currentWeatherEntity: CurrentWeatherEntity)

    @Query("Select * from current_weather where id=$CURRENT_WEATHER_ID")
    fun getCurrentWeather(): LiveData<CurrentWeatherEntity>
}