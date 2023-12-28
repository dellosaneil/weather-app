package com.thelazybattley.domain.di

import com.thelazybattley.domain.local.usecase.GetLocationUseCase
import com.thelazybattley.domain.local.usecase.InsertLocationUseCase
import com.thelazybattley.domain.local.usecase.impl.GetLocationUseCaseImpl
import com.thelazybattley.domain.local.usecase.impl.InsertLocationUseCaseImpl
import com.thelazybattley.domain.network.usecase.GetCurrentWeather
import com.thelazybattley.domain.network.usecase.GetDailyForecast
import com.thelazybattley.domain.network.usecase.GetHourlyForecast
import com.thelazybattley.domain.network.usecase.impl.GetCurrentWeatherImpl
import com.thelazybattley.domain.network.usecase.impl.GetDailyForecastImpl
import com.thelazybattley.domain.network.usecase.impl.GetHourlyForecastImpl
import com.thelazybattley.domain.usecase.GetAddress
import com.thelazybattley.domain.usecase.SearchAddressList
import com.thelazybattley.domain.usecase.impl.GetAddressImpl
import com.thelazybattley.domain.usecase.impl.SearchAddressListImpl
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

    @Binds
    abstract fun bindsGetCoordinates(impl: SearchAddressListImpl): SearchAddressList

    @Binds
    abstract fun bindsGetAddress(impl: GetAddressImpl): GetAddress

    @Binds
    abstract fun bindsGetLocationUseCase(impl: GetLocationUseCaseImpl): GetLocationUseCase

    @Binds
    abstract fun bindsInsertLocationUseCase(impl: InsertLocationUseCaseImpl): InsertLocationUseCase

}
