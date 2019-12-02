package com.sample.weather.di

import com.sample.weather.data.db.repository.ForecastRepository
import com.sample.weather.data.db.repository.ForecastRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(repositoryImpl: ForecastRepositoryImpl): ForecastRepository
}