package com.thelazybattley.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention
@Qualifier
annotation class MainDispatcher

@Retention
@Qualifier
annotation class IoDispatcher

@Retention
@Qualifier
annotation class DefaultDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @MainDispatcher
    @Singleton
    @Provides
    fun provideMainDispatcher() = Dispatchers.Main.immediate

    @IoDispatcher
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @DefaultDispatcher
    @Singleton
    @Provides
    fun provideDefaultDispatcher() = Dispatchers.Default

}
