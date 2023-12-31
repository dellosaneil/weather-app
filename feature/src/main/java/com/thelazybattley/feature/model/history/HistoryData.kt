package com.thelazybattley.feature.model.history

data class HistoryData(
    val daily: HistoryDaily,
    val timezone: String
) {
    companion object {
        fun createDummy() = HistoryData(
            daily = HistoryDaily.createDummy(),
            timezone = "GMT+8"
        )
    }
}
