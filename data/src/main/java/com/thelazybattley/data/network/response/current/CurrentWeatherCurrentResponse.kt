package com.thelazybattley.data.network.response.current

import com.google.gson.annotations.SerializedName

data class CurrentWeatherCurrentResponse(
    @SerializedName("temperature_2m") val temperature2m: Double,
    @SerializedName("wind_direction_10m") val windDirection10m: Int,
    @SerializedName("wind_speed_10m") val windSpeed10m: Double,
    @SerializedName("relative_humidity_2m") val relativeHumidity2m: Int,
    val apparentTemperature: Double,
    val isDay: Int,
    val precipitation: Double,
    val time: Int,
    val weatherCode: Int,
    val cloudCover: Int,
    val surfacePressure: Double
)
