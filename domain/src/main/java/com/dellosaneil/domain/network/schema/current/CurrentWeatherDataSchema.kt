package com.dellosaneil.domain.network.schema.current

data class CurrentWeatherDataSchema(
    val dt: Int,
    val main: CurrentWeatherMainSchema,
    val name: String,
    val sys: CurrentWeatherSysSchema,
    val weather: List<CurrentWeatherSchema>,
    val wind: CurrentWeatherWindSchema
)
