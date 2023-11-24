package com.dellosaneil.domain.network.schema

data class CurrentWeatherCurrentSchema(
    val cloud: Int,
    val condition: CurrentWeatherConditionSchema,
    val feelslikeC: Double,
    val humidity: Int,
    val isDay: Int,
    val lastUpdated: String,
    val lastUpdatedEpoch: Int,
    val precipIn: Double,
    val precipMm: Double,
    val tempC: Double,
    val windKph: Double,
)
