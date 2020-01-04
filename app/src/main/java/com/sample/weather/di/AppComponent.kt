package com.sample.weather.di

import android.app.Application
import com.sample.weather.ForecastApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RoomModule::class,
        InterceptorModule::class,
        RepositoryModule::class,
        LocationBindModule::class,
        LocationProviderModule::class,
        MainActivityModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: ForecastApplication)

}