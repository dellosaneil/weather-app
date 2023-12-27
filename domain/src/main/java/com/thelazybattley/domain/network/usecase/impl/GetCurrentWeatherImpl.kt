package com.thelazybattley.domain.network.usecase.impl

import com.thelazybattley.domain.network.usecase.GetCurrentWeather
import com.thelazybattley.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : GetCurrentWeather {

    override suspend fun invoke(latitude: String, longitude: String) =
        weatherRepository.getCurrentWeather(
            latitude = latitude, longitude = longitude
        )

}
