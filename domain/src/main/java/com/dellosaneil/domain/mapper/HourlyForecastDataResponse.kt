package com.dellosaneil.domain.mapper

import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastDataSchema
import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastHourlySchema
import com.thelazybattley.data.network.response.hourlyforecast.HourlyForecastDataResponse

val HourlyForecastDataResponse.toSchema
    get() = HourlyForecastDataSchema(
        hourly = hourly.run {
            HourlyForecastHourlySchema(
                precipitationProbability = precipitationProbability,
                temperature2m = temperature2m,
                time = time,
                weatherCode = weatherCode,
                surfacePressure = surfacePressure,
                cloudCover = cloudCover,
                visibility = visibility,
                apparentTemperature = apparentTemperature,
                relativeHumidity2m = relativeHumidity2m,
                windSpeed10m = windSpeed10m,
                windDirection10m = windDirection10m
            )
        }
    )
