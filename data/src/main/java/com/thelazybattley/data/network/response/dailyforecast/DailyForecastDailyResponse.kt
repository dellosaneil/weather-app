package com.thelazybattley.data.network.response.dailyforecast

import com.google.gson.annotations.SerializedName

data class DailyForecastDailyResponse(
    @SerializedName("temperature_2m_max") val temperature2mMax: List<Double>,
    @SerializedName("temperature_2m_min") val temperature2mMin: List<Double>,
    val daylightDuration: List<Double>,
    val precipitationProbabilityMax: List<Int>,
    val sunrise: List<Int>,
    val sunset: List<Int>,
    val time: List<Int>,
    val weatherCode: List<Int>
)
