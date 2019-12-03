package com.sample.weather.di

import com.sample.weather.data.provider.UnitProvider
import com.sample.weather.data.provider.UnitProviderImpl
import com.sample.weather.ui.weather.CurrentWeatherFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [CurrentWeatherFragmentComponent::class])
abstract class CurrentWeatherModule {
    @Binds
    @IntoMap
    @ClassKey(CurrentWeatherFragment::class)
    abstract fun bindCurrentWeatherFragmentInjectorFactory(
        factory: CurrentWeatherFragmentComponent.Factory
    ): AndroidInjector.Factory<*>

    @Binds
    abstract fun bindUnitSystemProvider(unitProviderImpl: UnitProviderImpl): UnitProvider
}