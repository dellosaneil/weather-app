package com.thelazybattley.data.network.service

import com.thelazybattley.data.network.response.currentweather.CurrentWeatherDataResponse
import com.thelazybattley.data.network.response.hourlyforecast.HourlyForecastDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ): CurrentWeatherDataResponse

    @GET("forecast")
    suspend fun getHourlyForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ) : HourlyForecastDataResponse

}