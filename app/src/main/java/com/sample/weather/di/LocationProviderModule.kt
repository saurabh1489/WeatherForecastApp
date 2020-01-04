package com.sample.weather.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sample.weather.data.provider.LocationProvider
import com.sample.weather.data.provider.LocationProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class LocationProviderModule {

    @Provides
    fun locationProviderClient(@AppContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

}