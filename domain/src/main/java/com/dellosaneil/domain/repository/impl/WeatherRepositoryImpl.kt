package com.dellosaneil.domain.repository.impl

import com.dellosaneil.domain.enums.WeatherParams
import com.dellosaneil.domain.mapper.toSchema
import com.dellosaneil.domain.network.schema.current.CurrentWeatherDataSchema
import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastDataSchema
import com.dellosaneil.domain.repository.WeatherRepository
import com.thelazybattley.data.network.service.OpenMateoService
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val openMateoService: OpenMateoService
) : WeatherRepository {

    companion object {
        private const val TIME_FORMAT = "unixtime"
        private const val TIME_ZONE = "auto"
    }

    override suspend fun getCurrentWeather(
        latitude: String,
        longitude: String
    ): Result<CurrentWeatherDataSchema> {
        return try {
            val schema = openMateoService.getCurrentWeather(
                latitude = latitude,
                longitude = longitude,
                timeFormat = TIME_FORMAT,
                timezone = TIME_ZONE,
                params = WeatherParams.currentWeatherParams()
            ).toSchema
            Result.success(schema)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getHourlyForecast(
        latitude: String,
        longitude: String
    ): Result<HourlyForecastDataSchema> {
        return try {
            val schema = openMateoService.getHourlyForecast(
                latitude = latitude,
                longitude = longitude,
                params = WeatherParams.hourlyWeatherForecast(),
                timeFormat = TIME_FORMAT,
                timezone = TIME_ZONE
            ).toSchema
            Result.success(schema)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDailyForecast(
        latitude: String,
        longitude: String
    ) = try {
        val schema = openMateoService.getDailyForecast(
            latitude = latitude,
            longitude = longitude,
            timezone = TIME_ZONE,
            timeFormat = TIME_FORMAT,
            params = WeatherParams.dailyWeatherForecast()
        ).toSchema
        Result.success(schema)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
