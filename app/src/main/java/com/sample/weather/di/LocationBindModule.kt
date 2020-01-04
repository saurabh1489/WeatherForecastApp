package com.sample.weather.di

import com.sample.weather.data.provider.LocationProvider
import com.sample.weather.data.provider.LocationProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class LocationBindModule {
    @Binds
    abstract fun bindLocationProvider(locationProviderImpl: LocationProviderImpl): LocationProvider
}