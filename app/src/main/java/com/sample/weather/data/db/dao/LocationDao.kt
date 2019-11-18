package com.sample.weather.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.weather.data.db.entity.LOCATION_ID
import com.sample.weather.data.db.entity.LocationEntity

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAndInsert(locationEntity: LocationEntity)

    @Query("Select * from weather_location where id=$LOCATION_ID")
    suspend fun getCurrentWeather(): LocationEntity

}