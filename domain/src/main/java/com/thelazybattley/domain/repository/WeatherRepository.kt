package com.thelazybattley.domain.repository

import com.thelazybattley.domain.network.schema.current.CurrentWeatherDataSchema
import com.thelazybattley.domain.network.schema.dailyforecast.DailyForecastDataSchema
import com.thelazybattley.domain.network.schema.hourlyforecast.HourlyForecastDataSchema

interface WeatherRepository {

    suspend fun getCurrentWeather(latitude: String, longitude: String): Result<CurrentWeatherDataSchema>

    suspend fun getHourlyForecast(latitude: String, longitude: String) : Result<HourlyForecastDataSchema>

    suspend fun getDailyForecast(latitude: String, longitude: String) : Result<DailyForecastDataSchema>
}
