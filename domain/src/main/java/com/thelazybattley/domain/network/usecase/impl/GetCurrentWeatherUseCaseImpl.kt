package com.thelazybattley.domain.network.usecase.impl

import com.thelazybattley.domain.network.usecase.GetCurrentWeatherUseCase
import com.thelazybattley.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : GetCurrentWeatherUseCase {

    override suspend fun invoke(latitude: String, longitude: String) =
        weatherRepository.getCurrentWeather(
            latitude = latitude, longitude = longitude
        )

}
