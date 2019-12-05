package com.sample.weather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.weather.data.db.entity.LOCATION_ID
import com.sample.weather.data.db.entity.WeatherLocation

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAndInsert(locationEntity: WeatherLocation)

    @Query("Select * from weather_location where id=$LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>

}