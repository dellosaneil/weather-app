package com.thelazybattley.domain.di

import com.thelazybattley.domain.repository.WeatherRepository
import com.thelazybattley.domain.repository.impl.WeatherRepositoryImpl
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
