package com.thelazybattley.domain.mapper

import com.thelazybattley.data.network.response.current.CurrentWeatherDataResponse
import com.thelazybattley.domain.network.schema.current.CurrentWeatherCurrentSchema
import com.thelazybattley.domain.network.schema.current.CurrentWeatherDataSchema

val CurrentWeatherDataResponse.toSchema
    get() = run {
        val timeZone = timezone
        CurrentWeatherDataSchema(
            timeZone = timeZone,
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
    }
