package com.thelazybattley.archive.mapper

import com.thelazybattley.domain.network.schema.history.HistoryDataSchema
import com.thelazybattley.common.model.WeatherCondition
import com.thelazybattley.common.util.epochToMillis

val HistoryDataSchema.toData
    get() = com.thelazybattley.archive.model.HistoryData(
        daily = daily.run {
            com.thelazybattley.archive.model.HistoryDaily(
                precipitationHours = precipitationHours,
                precipitationSum = precipitationSum,
                temperature2mMax = temperature2mMax,
                temperature2mMean = temperature2mMean,
                weatherCode = weatherCode.map {
                    WeatherCondition.toWeatherCondition(
                        id = it,
                        isDay = true
                    )
                },
                temperature2mMin = temperature2mMin,
                time = time.map { it.epochToMillis }
            )
        },
        timezone = timezone
    )
