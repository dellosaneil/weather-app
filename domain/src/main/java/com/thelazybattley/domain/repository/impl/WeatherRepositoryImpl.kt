package com.thelazybattley.domain.repository.impl

import com.thelazybattley.data.local.dao.UserLocationDao
import com.thelazybattley.data.network.service.OpenMateoService
import com.thelazybattley.domain.enums.WeatherParams
import com.thelazybattley.domain.local.model.userlocation.UserLocation
import com.thelazybattley.domain.mapper.toSchema
import com.thelazybattley.domain.network.schema.current.CurrentWeatherDataSchema
import com.thelazybattley.domain.network.schema.hourlyforecast.HourlyForecastDataSchema
import com.thelazybattley.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val openMateoService: OpenMateoService,
    private val userLocationDao: UserLocationDao
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

    override suspend fun getLocation(): Flow<UserLocation?> {
        return userLocationDao.getLocation().map {
            if (it == null) {
                return@map null
            }
            UserLocation.fromEntity(entity = it)
        }
    }

    override suspend fun insertLocation(userLocation: UserLocation) {
        userLocationDao.insertLocation(location = UserLocation.toEntity(location = userLocation))
    }
}
