package com.thelazybattley.domain.network.schema.hourlyforecast

data class HourlyForecastDataSchema(
    val hourly: HourlyForecastHourlySchema,
    val timeZone: String
)
