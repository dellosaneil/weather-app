package com.thelazybattley.domain.mapper

import com.thelazybattley.data.network.response.history.HistoryDataResponse
import com.thelazybattley.domain.network.schema.history.HistoryDailySchema
import com.thelazybattley.domain.network.schema.history.HistoryDataSchema

val HistoryDataResponse.toSchema
    get() = HistoryDataSchema(
        daily = daily.run {
            HistoryDailySchema(
                precipitationHours = precipitationHours,
                precipitationSum = precipitationSum,
                temperature2mMax = temperature2mMax,
                temperature2mMean = temperature2mMean,
                temperature2mMin = temperature2mMin,
                time = time,
                weatherCode = weatherCode
            )
        },
        timezone = timezone
    )
