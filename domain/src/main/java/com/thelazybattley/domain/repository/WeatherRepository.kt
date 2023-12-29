package com.thelazybattley.domain.repository

import com.thelazybattley.domain.local.model.userlocation.UserLocation
import com.thelazybattley.domain.network.schema.current.CurrentWeatherDataSchema
import com.thelazybattley.domain.network.schema.dailyforecast.DailyForecastDataSchema
import com.thelazybattley.domain.network.schema.history.HistoryDataSchema
import com.thelazybattley.domain.network.schema.hourlyforecast.HourlyForecastDataSchema
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getCurrentWeather(
        latitude: String,
        longitude: String
    ): Result<CurrentWeatherDataSchema>

    suspend fun getHourlyForecast(
        latitude: String,
        longitude: String
    ): Result<HourlyForecastDataSchema>

    suspend fun getDailyForecast(
        latitude: String,
        longitude: String
    ): Result<DailyForecastDataSchema>

    suspend fun getLocation(): Flow<UserLocation?>

    suspend fun insertLocation(userLocation: UserLocation)

    suspend fun getHistory(
        latitude: String,
        longitude: String,
        startDate: String,
        endDate: String
    ) : Result<HistoryDataSchema>
}
