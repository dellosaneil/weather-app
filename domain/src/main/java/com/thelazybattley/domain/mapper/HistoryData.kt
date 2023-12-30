package com.thelazybattley.domain.mapper

import com.thelazybattley.data.network.response.history.HistoryDataResponse
import com.thelazybattley.domain.network.schema.history.HistoryDailySchema
import com.thelazybattley.domain.network.schema.history.HistoryDataSchema

val HistoryDataResponse.toSchema
    get() = HistoryDataSchema(
        daily = daily.run {
            val size = listOf(
                precipitationHours.filterNotNull(),
                precipitationSum.filterNotNull(),
                temperature2mMax.filterNotNull(),
                temperature2mMean.filterNotNull(),
                temperature2mMin.filterNotNull(),
                time.filterNotNull(),
                weatherCode.filterNotNull()
            ).minOf { it.size }

            HistoryDailySchema(
                precipitationHours = precipitationHours.filterNotNull().take(size),
                precipitationSum = precipitationSum.filterNotNull().take(size),
                temperature2mMax = temperature2mMax.filterNotNull().take(size),
                temperature2mMean = temperature2mMean.filterNotNull().take(size),
                temperature2mMin = temperature2mMin.filterNotNull().take(size),
                time = time.filterNotNull().take(size),
                weatherCode = weatherCode.filterNotNull().take(size)
            )
        },
        timezone = timezone
    )
