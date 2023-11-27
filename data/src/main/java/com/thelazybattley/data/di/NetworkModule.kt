package com.thelazybattley.data.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.thelazybattley.data.network.service.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        authInterceptor: AuthInterceptor
    ) : Retrofit {
        val gson = GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(15))
            .readTimeout(Duration.ofSeconds(15))
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit) = retrofit.create(WeatherService::class.java)


}
