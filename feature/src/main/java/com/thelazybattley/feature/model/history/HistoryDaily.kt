package com.thelazybattley.feature.model.history

import com.thelazybattley.feature.model.currentweather.WeatherCondition

data class HistoryDaily(
    val precipitationHours: Double,
    val precipitationSum: Double,
    val temperature2mMax: Double,
    val temperature2mMean: Double,
    val temperature2mMin: Double,
    val time: Long,
    val weatherCode: WeatherCondition
)
