package com.thelazybattley.domain.network.schema.history

data class HistoryDataSchema(
    val daily: HistoryDailySchema,
    val timezone: String
)
