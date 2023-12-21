package com.dellosaneil.domain.di

import com.dellosaneil.domain.network.usecase.GetCurrentWeather
import com.dellosaneil.domain.network.usecase.GetDailyForecast
import com.dellosaneil.domain.network.usecase.GetHourlyForecast
import com.dellosaneil.domain.network.usecase.impl.GetCurrentWeatherImpl
import com.dellosaneil.domain.network.usecase.impl.GetDailyForecastImpl
import com.dellosaneil.domain.network.usecase.impl.GetHourlyForecastImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindsGetCurrentWeather(impl: GetCurrentWeatherImpl): GetCurrentWeather

    @Binds
    abstract fun bindsGetHourlyForecast(impl: GetHourlyForecastImpl): GetHourlyForecast

    @Binds
    abstract fun bindsGetDailyForecast(impl: GetDailyForecastImpl): GetDailyForecast

}
