package com.thelazybattley.domain.network.usecase.impl

import com.thelazybattley.domain.network.usecase.GetHourlyForecastUseCase
import com.thelazybattley.domain.repository.WeatherRepository
import javax.inject.Inject

class GetHourlyForecastUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : GetHourlyForecastUseCase {

    override suspend fun invoke(latitude: String, longitude: String) =
        weatherRepository.getHourlyForecast(
            latitude = latitude,
            longitude = longitude
        )

}
