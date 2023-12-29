package com.thelazybattley.feature.model.history

data class HistoryDaily(
    val precipitationHours: List<Double>,
    val precipitationSum: List<Double>,
    val temperature2mMax: List<Double>,
    val temperature2mMean: List<Double>,
    val temperature2mMin: List<Double>,
    val time: List<Long>,
    val weatherCode: List<Int>
)
