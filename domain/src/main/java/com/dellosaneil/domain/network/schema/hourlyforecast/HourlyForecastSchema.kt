package com.dellosaneil.domain.network.schema.hourlyforecast

data class HourlyForecastSchema(
    val dt: Int,
    val main: HourlyForecastMainSchema,
    val pop: Double,
    val weather: List<HourlyForecastWeatherSchema>,
    val wind: HourlyForecastWindSchema
)
