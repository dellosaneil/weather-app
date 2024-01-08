package com.thelazybattley.current.mapper

import com.thelazybattley.common.model.WeatherCondition
import com.thelazybattley.common.util.epochToMillis
import com.thelazybattley.current.model.current.CurrentWeatherCurrent
import com.thelazybattley.current.model.current.CurrentWeatherData
import com.thelazybattley.domain.network.schema.current.CurrentWeatherDataSchema

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
