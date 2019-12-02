package com.sample.weather.di

import com.sample.weather.ui.weather.CurrentWeatherFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface CurrentWeatherFragmentComponent: AndroidInjector<CurrentWeatherFragment> {
    @Subcomponent.Factory
    interface Factory: AndroidInjector.Factory<CurrentWeatherFragment>
}