package com.thelazybattley.data.network.response.hourlyforecast

import com.google.gson.annotations.SerializedName

data class HourlyForecastHourlyResponse(
    @SerializedName("temperature_2m") val temperature2m: List<Double>,
    @SerializedName("relative_humidity_2m") val relativeHumidity2m: List<Int>,
    @SerializedName("wind_speed_10m") val windSpeed10m: List<Double>,
    @SerializedName("wind_direction_10m") val windDirection10m: List<Int>,
    val precipitationProbability: List<Int>,
    val time: List<String>,
    val weatherCode: List<Int>,
    val surfacePressure: List<Double>,
    val cloudCover: List<Int>,
    val visibility: List<Int>,
    val apparentTemperature: List<Double>,
    val precipitation: List<Double>
)
