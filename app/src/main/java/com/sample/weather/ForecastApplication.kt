package com.sample.weather

import android.app.Application
import com.sample.weather.data.db.WeatherDatabase
import com.sample.weather.data.db.repository.ForecastRepository
import com.sample.weather.data.db.repository.ForecastRepositoryImpl
import com.sample.weather.data.network.*
import com.sample.weather.ui.weather.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }
        bind() from singleton { WeatherApiService(instance(),instance()) }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance()) }
        bind() from provider { EnumConverterFactory() }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
    }
}