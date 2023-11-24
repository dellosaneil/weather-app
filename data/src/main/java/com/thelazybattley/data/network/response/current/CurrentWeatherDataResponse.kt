package com.thelazybattley.data.network.response.current

data class CurrentWeatherDataResponse(
    val current: CurrentWeatherCurrentResponse,
    val location: CurrentWeatherLocationResponse
)
