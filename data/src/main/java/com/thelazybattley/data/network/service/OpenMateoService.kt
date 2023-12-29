package com.thelazybattley.data.network.service

import com.thelazybattley.data.network.response.current.CurrentWeatherDataResponse
import com.thelazybattley.data.network.response.dailyforecast.DailyForecastDataResponse
import com.thelazybattley.data.network.response.hourlyforecast.HourlyForecastDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMateoService {

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("current") params: String,
        @Query("timezone") timezone: String
    ): CurrentWeatherDataResponse

    @GET("forecast")
    suspend fun getDailyForecast(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("daily") params: String,
        @Query("timezone") timezone: String
    ): DailyForecastDataResponse

    @GET("forecast")
    suspend fun getHourlyForecast(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("hourly") params: String,
        @Query("timezone") timezone: String
    ): HourlyForecastDataResponse

}
