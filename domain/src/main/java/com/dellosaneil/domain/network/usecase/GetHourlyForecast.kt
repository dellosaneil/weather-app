package com.dellosaneil.domain.network.usecase

import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastDataSchema

interface GetHourlyForecast {

    suspend operator fun invoke(latitude: String, longitude: String): Result<HourlyForecastDataSchema>
}
