package com.sample.weather.data.db.repository

import androidx.lifecycle.LiveData
import com.sample.weather.data.db.dao.CurrentWeatherDao
import com.sample.weather.data.db.dao.LocationDao
import com.sample.weather.data.db.entity.CurrentWeatherEntity
import com.sample.weather.data.db.entity.WeatherLocation
import com.sample.weather.data.network.WeatherApiService
import com.sample.weather.data.network.response.CurrentWeatherResponse
import com.sample.weather.data.network.response.NetworkBoundResource
import com.sample.weather.internal.UnitSystem
import com.sample.weather.vi.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ForecastRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val currentWeatherDao: CurrentWeatherDao,
    private val locationDao: LocationDao
) : ForecastRepository {

    override suspend fun getCurrentWeather(unit: UnitSystem): LiveData<Resource<CurrentWeatherEntity>> {
        return object : NetworkBoundResource<CurrentWeatherResponse, CurrentWeatherEntity>() {
            override val coroutineContext: CoroutineContext
                get() = Job() + Dispatchers.Main

            override fun loadFromDb(): LiveData<CurrentWeatherEntity> {
                return currentWeatherDao.getCurrentWeather()
            }

            override fun shouldFetch(data: CurrentWeatherEntity?): Boolean {
                return true
            }

            override fun createCall(): LiveData<CurrentWeatherResponse> {
                return weatherApiService.getCurrentWeather("Noida", unit)
            }

            override fun saveCallResult(item: CurrentWeatherResponse) {
                currentWeatherDao.updateAndInsert(item.current)
                locationDao.updateAndInsert(item.location)
            }

        }.asLiveData()
    }

    override suspend fun getLocation(): LiveData<WeatherLocation> {
        return locationDao.getLocation()
    }
}