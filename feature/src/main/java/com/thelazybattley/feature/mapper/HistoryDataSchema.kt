package com.thelazybattley.feature.mapper

import com.thelazybattley.domain.network.schema.history.HistoryDataSchema
import com.thelazybattley.feature.model.currentweather.WeatherCondition
import com.thelazybattley.feature.model.history.HistoryDaily
import com.thelazybattley.feature.model.history.HistoryData
import com.thelazybattley.feature.util.epochToMillis

val HistoryDataSchema.toData
    get() = HistoryData(
        daily = daily.run {
            HistoryDaily(
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
