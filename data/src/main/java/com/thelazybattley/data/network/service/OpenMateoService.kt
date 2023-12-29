package com.thelazybattley.data.network.service

import com.thelazybattley.data.network.response.current.CurrentWeatherDataResponse
import com.thelazybattley.data.network.response.dailyforecast.DailyForecastDataResponse
import com.thelazybattley.data.network.response.history.HistoryDataResponse
import com.thelazybattley.data.network.response.hourlyforecast.HourlyForecastDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMateoService {

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("current") params: String,
        @Query("timezone") timezone: String,
        @Query("timeformat") timeFormat: String
    ): CurrentWeatherDataResponse

    @GET("forecast")
    suspend fun getDailyForecast(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("daily") params: String,
        @Query("timezone") timezone: String,
        @Query("timeformat") timeFormat: String
    ): DailyForecastDataResponse

    @GET("forecast")
    suspend fun getHourlyForecast(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("hourly") params: String,
        @Query("timezone") timezone: String,
        @Query("timeformat") timeFormat: String
    ): HourlyForecastDataResponse

    @GET("history")
    suspend fun getHistory(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("daily") params: String
    ): HistoryDataResponse

}


//https://archive-api.open-meteo.com/v1/archive?latitude=52.52&longitude=13.41&start_date=2023-10-06&end_date=2023-12-29&daily=weather_code,temperature_2m_max,temperature_2m_min,temperature_2m_mean,precipitation_sum,precipitation_hours
