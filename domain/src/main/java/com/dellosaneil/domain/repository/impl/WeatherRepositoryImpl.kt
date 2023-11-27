package com.dellosaneil.domain.repository.impl

import com.dellosaneil.domain.mapper.toSchema
import com.dellosaneil.domain.network.schema.current.CurrentWeatherDataSchema
import com.dellosaneil.domain.repository.WeatherRepository
import com.thelazybattley.data.network.service.WeatherService
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {
    override suspend fun getCurrentWeather(latitude: String, longitude: String): Result<CurrentWeatherDataSchema> {
        return try {
            val schema = weatherService.getCurrentWeather(latitude, longitude).toSchema
            Result.success(schema)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
