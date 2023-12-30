package com.thelazybattley.data.network.response.history

import com.google.gson.annotations.SerializedName

data class HistoryDailyResponse(
    val precipitationHours: List<Double?>,
    val precipitationSum: List<Double?>,
    @SerializedName("temperature_2m_max") val temperature2mMax: List<Double?>,
    @SerializedName("temperature_2m_mean") val temperature2mMean: List<Double?>,
    @SerializedName("temperature_2m_min") val temperature2mMin: List<Double?>,
    val time: List<Int?>,
    val weatherCode: List<Int?>
)
