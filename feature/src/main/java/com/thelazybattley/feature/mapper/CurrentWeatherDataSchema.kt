package com.thelazybattley.feature.mapper

import com.thelazybattley.domain.network.schema.current.CurrentWeatherDataSchema
import com.thelazybattley.feature.model.currentweather.CurrentWeatherCurrent
import com.thelazybattley.feature.model.currentweather.CurrentWeatherData
import com.thelazybattley.feature.model.currentweather.WeatherCondition
import com.thelazybattley.feature.util.epochToMillis

val CurrentWeatherDataSchema.toData
    get() = run {
        CurrentWeatherData(
            timeZone = timeZone,
            current = current.run {
                CurrentWeatherCurrent(
                    apparentTemperature = apparentTemperature,
                    isDay = isDay == 1,
                    precipitation = precipitation,
                    temperature2m = temperature2m,
                    time = time.epochToMillis,
                    windDirection10m = windDirection10m,
                    windSpeed10m = windSpeed10m,
                    cloudCover = cloudCover,
                    surfacePressure = surfacePressure,
                    relativeHumidity2m = relativeHumidity2m,
                    weatherCondition = WeatherCondition.toWeatherCondition(
                        id = weatherCode,
                        isDay = isDay == 1
                    )
                )
            }
        )
    }
