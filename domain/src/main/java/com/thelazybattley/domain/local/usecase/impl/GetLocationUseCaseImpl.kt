package com.thelazybattley.domain.local.usecase.impl

import com.thelazybattley.domain.local.usecase.GetLocationUseCase
import com.thelazybattley.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLocationUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
): GetLocationUseCase {
    override suspend fun invoke() = repository.getLocation()
}
