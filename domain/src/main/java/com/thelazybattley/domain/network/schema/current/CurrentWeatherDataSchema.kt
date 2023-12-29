package com.thelazybattley.domain.network.schema.current

data class CurrentWeatherDataSchema(
    val current: CurrentWeatherCurrentSchema,
    val timeZone: String
)
