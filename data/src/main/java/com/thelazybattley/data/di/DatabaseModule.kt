package com.thelazybattley.data.di

import android.content.Context
import androidx.room.Room
import com.thelazybattley.data.local.db.WeatherDatabase
import com.thelazybattley.data.local.db.WeatherDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context = context,
        klass = WeatherDatabase::class.java,
        name = DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideLocationDao(weatherDatabase: WeatherDatabase) = weatherDatabase.locationDao()

}
