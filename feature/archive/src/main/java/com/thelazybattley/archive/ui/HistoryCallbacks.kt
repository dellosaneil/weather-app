package com.thelazybattley.archive.ui

interface HistoryCallbacks {

    fun selectLegend(legend: HistoryLegend)

    fun highlightData(xStartOffset: Float, xEndOffset: Float, chartWidth: Float)

    fun selectDateRange(startMillis: Long, endMillis: Long)
}
