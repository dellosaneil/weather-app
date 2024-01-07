package com.thelazybattley.domain.di

import com.thelazybattley.domain.local.usecase.GetLocationUseCase
import com.thelazybattley.domain.local.usecase.InsertLocationUseCase
import com.thelazybattley.domain.local.usecase.impl.GetLocationUseCaseImpl
import com.thelazybattley.domain.local.usecase.impl.InsertLocationUseCaseImpl
import com.thelazybattley.domain.network.usecase.GetCurrentWeatherUseCase
import com.thelazybattley.domain.network.usecase.GetDailyForecastUseCase
import com.thelazybattley.domain.network.usecase.GetHistoryUseCase
import com.thelazybattley.domain.network.usecase.GetHourlyForecastUseCase
import com.thelazybattley.domain.network.usecase.impl.GetCurrentWeatherUseCaseImpl
import com.thelazybattley.domain.network.usecase.impl.GetDailyForecastUseCaseImpl
import com.thelazybattley.domain.network.usecase.impl.GetHistoryUseCaseImpl
import com.thelazybattley.domain.network.usecase.impl.GetHourlyForecastUseCaseImpl
import com.thelazybattley.domain.usecase.GetAddress
import com.thelazybattley.domain.usecase.LocalDateFormatterUseCase
import com.thelazybattley.domain.usecase.SearchAddressList
import com.thelazybattley.domain.usecase.impl.GetAddressImpl
import com.thelazybattley.domain.usecase.impl.LocalDateFormatterUseCaseImpl
import com.thelazybattley.domain.usecase.impl.SearchAddressListImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindsGetCurrentWeather(impl: GetCurrentWeatherUseCaseImpl): GetCurrentWeatherUseCase

    @Binds
    abstract fun bindsGetHourlyForecast(impl: GetHourlyForecastUseCaseImpl): GetHourlyForecastUseCase

    @Binds
    abstract fun bindsGetDailyForecast(impl: GetDailyForecastUseCaseImpl): GetDailyForecastUseCase

    @Binds
    abstract fun bindsGetCoordinates(impl: SearchAddressListImpl): SearchAddressList

    @Binds
    abstract fun bindsGetAddress(impl: GetAddressImpl): GetAddress

    @Binds
    abstract fun bindsGetLocationUseCase(impl: GetLocationUseCaseImpl): GetLocationUseCase

    @Binds
    abstract fun bindsInsertLocationUseCase(impl: InsertLocationUseCaseImpl): InsertLocationUseCase

    @Binds
    abstract fun bindsGetHistoryUseCase(impl: GetHistoryUseCaseImpl): GetHistoryUseCase

    @Binds
    abstract fun bindsLocalDateFormatterUseCase(impl: LocalDateFormatterUseCaseImpl): LocalDateFormatterUseCase

}
