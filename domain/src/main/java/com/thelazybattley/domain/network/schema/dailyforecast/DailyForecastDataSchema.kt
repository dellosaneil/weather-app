package com.thelazybattley.domain.network.schema.dailyforecast

data class DailyForecastDataSchema(
    val daily: DailyForecastDailySchema,
    val timeZone: String
)
