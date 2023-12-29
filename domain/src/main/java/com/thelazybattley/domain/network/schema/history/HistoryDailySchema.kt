package com.thelazybattley.domain.network.schema.history

data class HistoryDailySchema(
    val precipitationHours: List<Double>,
    val precipitationSum: List<Double>,
    val temperature2mMax: List<Double>,
    val temperature2mMean: List<Double>,
    val temperature2mMin: List<Double>,
    val time: List<String>,
    val weatherCode: List<Int>
)
