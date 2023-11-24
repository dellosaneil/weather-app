package com.dellosaneil.domain.network.schema

data class CurrentWeatherDataSchema(
    val current: CurrentWeatherCurrentSchema,
    val location: CurrentWeatherLocationSchema
)
