package com.dellosaneil.domain.network.schema.hourlyforecast

data class HourlyForecastDataSchema(
    val city: HourlyForecastCitySchema,
    val list: List<HourlyForecastSchema>
)
