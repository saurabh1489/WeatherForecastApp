package com.sample.weather.data.db.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.sample.weather.data.db.dao.CurrentWeatherDao
import com.sample.weather.data.db.dao.LocationDao
import com.sample.weather.data.db.entity.CurrentWeatherEntity
import com.sample.weather.data.db.entity.WeatherLocation
import com.sample.weather.data.network.WeatherApiService
import com.sample.weather.data.network.response.CurrentWeatherResponse
import com.sample.weather.data.network.response.NetworkBoundResource
import com.sample.weather.data.provider.LocationProvider
import com.sample.weather.internal.UnitSystem
import com.sample.weather.vi.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ForecastRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val currentWeatherDao: CurrentWeatherDao,
    private val locationDao: LocationDao,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    val TAG = "Awasthi"

    override suspend fun getCurrentWeather(unit: UnitSystem): LiveData<Resource<CurrentWeatherEntity>> {
        return object : NetworkBoundResource<CurrentWeatherResponse, CurrentWeatherEntity>() {
            override val coroutineContext: CoroutineContext
                get() = Job() + Dispatchers.Main

            override suspend fun loadFromDb(): LiveData<CurrentWeatherEntity> {
                Log.d(TAG,"loadFromDb()")
                return currentWeatherDao.getCurrentWeather()
            }

            override suspend fun shouldFetch(data: CurrentWeatherEntity?): Boolean {
                Log.d(TAG,"shouldFetch()")
                var lastLocation:WeatherLocation? = null
                withContext(Dispatchers.IO) {
                    lastLocation = locationDao.getLocationNonLive()
                }
                return lastLocation == null || locationProvider.hasLocationChanged(lastLocation as WeatherLocation)
            }

            override suspend fun createCall(): LiveData<CurrentWeatherResponse> {
                Log.d(TAG,"createCall()")
                return weatherApiService.getCurrentWeather(locationProvider.getPreferredLocationString(), unit)
            }

            override suspend fun saveCallResult(item: CurrentWeatherResponse) {
                Log.d(TAG,"saveCallResult()")
                currentWeatherDao.updateAndInsert(item.current)
                locationDao.updateAndInsert(item.location)
            }

        }.asLiveData()
    }

    override suspend fun getLocation(): LiveData<WeatherLocation> {
        return locationDao.getLocation()
    }
}