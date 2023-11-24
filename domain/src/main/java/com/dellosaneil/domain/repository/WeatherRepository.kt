package com.dellosaneil.domain.repository

import com.dellosaneil.domain.network.schema.CurrentWeatherDataSchema

interface WeatherRepository {

    suspend fun getCurrentWeather(city: String): Result<CurrentWeatherDataSchema>
}
