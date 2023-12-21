package com.dellosaneil.domain.network.usecase

import com.dellosaneil.domain.network.schema.dailyforecast.DailyForecastDataSchema

interface GetDailyForecast {
    suspend operator fun invoke(
        latitude: String,
        longitude: String
    ) : Result<DailyForecastDataSchema>
}
