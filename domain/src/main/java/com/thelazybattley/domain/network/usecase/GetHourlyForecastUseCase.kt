package com.thelazybattley.domain.network.usecase

import com.thelazybattley.domain.network.schema.hourlyforecast.HourlyForecastDataSchema

interface GetHourlyForecastUseCase {

    suspend operator fun invoke(latitude: String, longitude: String): Result<HourlyForecastDataSchema>
}
