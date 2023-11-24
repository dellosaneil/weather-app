package com.dellosaneil.domain.di

import com.dellosaneil.domain.repository.WeatherRepository
import com.dellosaneil.domain.repository.impl.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

}
