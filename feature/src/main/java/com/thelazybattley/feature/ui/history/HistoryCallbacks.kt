package com.thelazybattley.feature.ui.history

interface HistoryCallbacks {

    fun selectLegend(legend: HistoryLegend)

    fun highlightData(xStartOffset: Float, xEndOffset: Float, chartWidth: Float)

    fun selectDateRange(startMillis: Long, endMillis: Long)
}
