package com.dellosaneil.domain.network.schema

data class CurrentWeatherLocationSchema(
    val country: String,
    val localtimeEpoch: Int,
    val name: String,
    val region: String,
    val tzId: String
)
