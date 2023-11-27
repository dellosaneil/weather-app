package com.dellosaneil.domain.repository

import com.dellosaneil.domain.network.schema.current.CurrentWeatherDataSchema
import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastDataSchema

interface WeatherRepository {

    suspend fun getCurrentWeather(latitude: String, longitude: String): Result<CurrentWeatherDataSchema>

    suspend fun getHourlyForecast(latitude: String, longitude: String) : Result<HourlyForecastDataSchema>
}
