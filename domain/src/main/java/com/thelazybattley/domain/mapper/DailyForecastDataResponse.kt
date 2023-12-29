package com.thelazybattley.domain.mapper

import com.thelazybattley.data.network.response.dailyforecast.DailyForecastDataResponse
import com.thelazybattley.domain.network.schema.dailyforecast.DailyForecastDailySchema
import com.thelazybattley.domain.network.schema.dailyforecast.DailyForecastDataSchema
import com.thelazybattley.domain.util.toEpoch

val DailyForecastDataResponse.toSchema
    get() = run {
        val timeZone = timezone
        DailyForecastDataSchema(
            timeZone = timeZone,
            daily = daily.run {
                DailyForecastDailySchema(
                    daylightDuration = daylightDuration,
                    precipitationProbabilityMax = precipitationProbabilityMax,
                    sunrise = sunrise.map {
                        it.toEpoch(
                            timeZone = timeZone
                        )
                    },
                    sunset = sunset.map {
                        it.toEpoch(
                            timeZone = timeZone
                        )
                    },
                    temperature2mMax = temperature2mMax,
                    temperature2mMin = temperature2mMin,
                    time = time.map {
                        it.toEpoch(
                            timeZone = timeZone
                        )
                    },
                    weatherCode = weatherCode
                )
            }
        )
    }
