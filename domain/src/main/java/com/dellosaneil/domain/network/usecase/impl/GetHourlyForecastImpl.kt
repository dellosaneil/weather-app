package com.dellosaneil.domain.network.usecase.impl

import com.dellosaneil.domain.network.usecase.GetHourlyForecast
import com.dellosaneil.domain.repository.WeatherRepository
import javax.inject.Inject

class GetHourlyForecastImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : GetHourlyForecast {

    override suspend fun invoke(latitude: String, longitude: String) =
        weatherRepository.getHourlyForecast(
            latitude = latitude,
            longitude = longitude
        )

}
