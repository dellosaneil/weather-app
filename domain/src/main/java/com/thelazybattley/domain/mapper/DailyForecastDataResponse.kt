package com.thelazybattley.domain.mapper

import com.thelazybattley.data.network.response.dailyforecast.DailyForecastDataResponse
import com.thelazybattley.domain.network.schema.dailyforecast.DailyForecastDailySchema
import com.thelazybattley.domain.network.schema.dailyforecast.DailyForecastDataSchema

val DailyForecastDataResponse.toSchema
    get() = run {
        val timeZone = timezone
        DailyForecastDataSchema(
            timeZone = timeZone,
            daily = daily.run {
                DailyForecastDailySchema(
                    daylightDuration = daylightDuration,
                    precipitationProbabilityMax = precipitationProbabilityMax,
                    sunrise = sunrise,
                    sunset = sunset,
                    temperature2mMax = temperature2mMax,
                    temperature2mMin = temperature2mMin,
                    time = time,
                    weatherCode = weatherCode
                )
            }
        )
    }
