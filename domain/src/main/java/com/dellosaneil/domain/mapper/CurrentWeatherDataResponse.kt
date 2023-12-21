package com.dellosaneil.domain.mapper

import com.dellosaneil.domain.network.schema.current.CurrentWeatherCurrentSchema
import com.dellosaneil.domain.network.schema.current.CurrentWeatherDataSchema
import com.thelazybattley.data.network.response.current.CurrentWeatherDataResponse

val CurrentWeatherDataResponse.toSchema
    get() = CurrentWeatherDataSchema(
        current = current.run {
            CurrentWeatherCurrentSchema(
                apparentTemperature = apparentTemperature,
                isDay = isDay,
                precipitation = precipitation,
                temperature2m = temperature2m,
                time = time,
                weatherCode = weatherCode,
                windDirection10m = windDirection10m,
                windSpeed10m = windSpeed10m,
                cloudCover = cloudCover,
                surfacePressure = surfacePressure,
                relativeHumidity2m = relativeHumidity2m
            )
        }
    )
