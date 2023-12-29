package com.thelazybattley.domain.network.usecase.impl

import com.thelazybattley.domain.network.usecase.GetHistoryUseCase
import com.thelazybattley.domain.repository.WeatherRepository
import javax.inject.Inject

class GetHistoryUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
) : GetHistoryUseCase {
    override suspend fun invoke(
        latitude: String,
        longitude: String,
        startDate: String,
        endDate: String
    ) = repository.getHistory(
        latitude = latitude,
        longitude = longitude,
        startDate = startDate,
        endDate = endDate
    )
}
