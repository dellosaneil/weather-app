package com.dellosaneil.domain.network.usecase.impl

import com.dellosaneil.domain.network.usecase.GetDailyForecast
import com.dellosaneil.domain.repository.WeatherRepository
import javax.inject.Inject

class GetDailyForecastImpl @Inject constructor(
    private val repository: WeatherRepository
) : GetDailyForecast {

    override suspend fun invoke(
        latitude: String,
        longitude: String
    ) = repository.getDailyForecast(latitude = latitude, longitude = longitude)
}
