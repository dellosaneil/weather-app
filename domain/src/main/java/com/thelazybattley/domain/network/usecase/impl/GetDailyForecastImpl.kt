package com.thelazybattley.domain.network.usecase.impl

import com.thelazybattley.domain.network.usecase.GetDailyForecast
import com.thelazybattley.domain.repository.WeatherRepository
import javax.inject.Inject

class GetDailyForecastImpl @Inject constructor(
    private val repository: WeatherRepository
) : GetDailyForecast {

    override suspend fun invoke(
        latitude: String,
        longitude: String
    ) = repository.getDailyForecast(latitude = latitude, longitude = longitude)
}
