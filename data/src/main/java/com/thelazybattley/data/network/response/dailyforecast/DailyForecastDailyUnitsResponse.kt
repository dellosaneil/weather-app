package com.thelazybattley.data.network.response.dailyforecast

import com.google.gson.annotations.SerializedName

data class DailyForecastDailyUnitsResponse(
    val daylightDuration: String,
    val precipitationProbabilityMax: String,
    val sunrise: String,
    val sunset: String,
    @SerializedName("temperature_2m_max") val temperature2mMax: String,
    @SerializedName("temperature_2m_min") val temperature2mMin: String,
    val time: String
)
