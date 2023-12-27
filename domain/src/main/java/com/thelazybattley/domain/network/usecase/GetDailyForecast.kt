package com.thelazybattley.domain.network.usecase

import com.thelazybattley.domain.network.schema.dailyforecast.DailyForecastDataSchema

interface GetDailyForecast {
    suspend operator fun invoke(
        latitude: String,
        longitude: String
    ) : Result<DailyForecastDataSchema>
}
