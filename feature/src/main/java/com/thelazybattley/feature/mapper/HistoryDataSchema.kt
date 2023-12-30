package com.thelazybattley.feature.mapper

import com.thelazybattley.domain.network.schema.history.HistoryDataSchema
import com.thelazybattley.feature.model.currentweather.WeatherCondition
import com.thelazybattley.feature.model.history.HistoryDaily
import com.thelazybattley.feature.model.history.HistoryData
import com.thelazybattley.feature.util.epochToMillis

val HistoryDataSchema.toData
    get() = HistoryData(
        daily = daily.run {
            val history = mutableListOf<HistoryDaily>()
            repeat(time.size) { index ->
                HistoryDaily(
                    precipitationHours = precipitationHours[index],
                    precipitationSum = precipitationSum[index],
                    temperature2mMax = temperature2mMax[index],
                    temperature2mMean = temperature2mMean[index],
                    temperature2mMin = temperature2mMin[index],
                    time = time.map { it.epochToMillis }[index],
                    weatherCode = WeatherCondition.toWeatherCondition(
                        id = weatherCode[index],
                        isDay = true
                    )
                )
            }
            history
        },
        timezone = timezone
    )
