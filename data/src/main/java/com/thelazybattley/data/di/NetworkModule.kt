package com.thelazybattley.data.di

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.thelazybattley.data.network.service.OpenMateoArchiveService
import com.thelazybattley.data.network.service.OpenMateoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.time.Duration
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention
@Qualifier
annotation class OpenMateoArchiveDispatcher

@Retention
@Qualifier
annotation class OpenMateoDispatcher

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @OpenMateoDispatcher
    @Provides
    fun provideOpenMateoRetrofit(
        @ApplicationContext context: Context
    ): Retrofit {
        val cacheSize = 10 * 1024 * 1024L
        val file = File(context.cacheDir, "http-cache")
        val cache = Cache(file, cacheSize)
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(15))
            .readTimeout(Duration.ofSeconds(15))
            .cache(cache = cache)
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    @OpenMateoArchiveDispatcher
    fun provideOpenMateoArchiveRetrofit(
        @ApplicationContext context: Context
    ): Retrofit {
        val cacheSize = 10 * 1024 * 1024L
        val file = File(context.cacheDir, "http-cache")
        val cache = Cache(file, cacheSize)
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(15))
            .readTimeout(Duration.ofSeconds(15))
            .cache(cache = cache)
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl("https://archive-api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Provides
    @Singleton
    fun provideOpenMateoService(@OpenMateoDispatcher retrofit: Retrofit) = retrofit.create(OpenMateoService::class.java)




    @Provides
    @Singleton
    fun provideArchiveOpenMateoService(@OpenMateoArchiveDispatcher retrofit: Retrofit) = retrofit.create(OpenMateoArchiveService::class.java)

}
