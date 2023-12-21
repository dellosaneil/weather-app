package com.thelazybattley.data.network.response.hourlyforecast

import com.google.gson.annotations.SerializedName

data class HourlyForecastHourlyUnitsResponse(
    val precipitationProbability: String,
    @SerializedName("temperature_2m") val temperature2m: String,
    val time: String,
    val weatherCode: String
)
