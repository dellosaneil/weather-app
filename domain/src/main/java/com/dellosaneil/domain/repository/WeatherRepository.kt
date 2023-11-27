package com.dellosaneil.domain.repository

import com.dellosaneil.domain.network.schema.current.CurrentWeatherDataSchema

interface WeatherRepository {

    suspend fun getCurrentWeather(latitude: String, longitude: String): Result<CurrentWeatherDataSchema>
}
