package com.thelazybattley.domain.network.usecase.impl

import com.thelazybattley.domain.network.usecase.GetHourlyForecast
import com.thelazybattley.domain.repository.WeatherRepository
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
