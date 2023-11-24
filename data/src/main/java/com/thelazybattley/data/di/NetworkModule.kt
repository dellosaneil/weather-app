package com.thelazybattley.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .build()
}
