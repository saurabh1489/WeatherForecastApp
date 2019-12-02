package com.sample.weather.di

import com.sample.weather.data.network.ConnectivityInterceptor
import com.sample.weather.data.network.ConnectivityInterceptorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class InterceptorModule {

    @Binds
    abstract fun bindConnectivityInterceptor(connectivityInterceptorImpl: ConnectivityInterceptorImpl): ConnectivityInterceptor
}