package com.dellosaneil.domain.mapper

import com.dellosaneil.domain.network.schema.dailyforecast.DailyForecastDailySchema
import com.dellosaneil.domain.network.schema.dailyforecast.DailyForecastDataSchema
import com.thelazybattley.data.network.response.dailyforecast.DailyForecastDataResponse

val DailyForecastDataResponse.toSchema
    get() = run {
        DailyForecastDataSchema(
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
