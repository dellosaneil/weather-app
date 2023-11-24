package com.thelazybattley.data.network.response.current

import com.google.gson.annotations.SerializedName

data class CurrentWeatherLocationResponse(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtimeEpoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    @SerializedName("tz_id") val tzId: String
)
