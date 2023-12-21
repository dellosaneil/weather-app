package com.thelazybattley.data.network.response.current

import com.google.gson.annotations.SerializedName

data class CurrentWeatherCurrentUnitsResponse(
    @SerializedName("temperature_2m") val temperature2m: String,
    @SerializedName("wind_direction_10m") val windDirection10m: String,
    @SerializedName("wind_speed_10m") val windSpeed10m: String,
    val apparentTemperature: String,
    val interval: String,
    val isDay: String,
    val precipitation: String,
    val time: String,
    val weatherCode: String,
)
