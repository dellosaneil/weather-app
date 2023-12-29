package com.thelazybattley.data.network.response.history

import com.google.gson.annotations.SerializedName

data class HistoryDailyUnitsResponse(
    val precipitationHours: String,
    val precipitationSum: String,
    @SerializedName("temperate_2m_max") val temperature2mMax: String,
    @SerializedName("temperate_2m_mean") val temperature2mMean: String,
    @SerializedName("temperate_2m_min") val temperature2mMin: String,
    val time: String,
    val weatherCode: String
)
