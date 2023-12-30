package com.thelazybattley.feature.model.history

data class HistoryData(
    val daily: List<HistoryDaily>,
    val timezone: String
)
