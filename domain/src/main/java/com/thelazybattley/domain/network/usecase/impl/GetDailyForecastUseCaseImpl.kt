package com.thelazybattley.domain.network.usecase.impl

import com.thelazybattley.domain.network.usecase.GetDailyForecastUseCase
import com.thelazybattley.domain.repository.WeatherRepository
import javax.inject.Inject

class GetDailyForecastUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
) : GetDailyForecastUseCase {

    override suspend fun invoke(
        latitude: String,
        longitude: String
    ) = repository.getDailyForecast(latitude = latitude, longitude = longitude)
}
