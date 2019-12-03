package com.sample.weather.di

import com.sample.weather.ui.dashboard.DashboardFragment
import com.sample.weather.ui.notifications.SettingsFragment
import com.sample.weather.ui.weather.CurrentWeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCurrentWeatherFragment(): CurrentWeatherFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment
}