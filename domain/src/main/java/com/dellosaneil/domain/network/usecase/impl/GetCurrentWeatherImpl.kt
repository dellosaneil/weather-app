package com.dellosaneil.domain.network.usecase.impl

import com.dellosaneil.domain.network.usecase.GetCurrentWeather
import com.dellosaneil.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : GetCurrentWeather {

    override suspend fun invoke(latitude: String, longitude: String) =
        weatherRepository.getCurrentWeather(
            latitude = latitude, longitude = longitude
        )

}