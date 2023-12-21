package com.dellosaneil.feature.mapper

import com.dellosaneil.domain.network.schema.current.CurrentWeatherDataSchema
import com.dellosaneil.feature.model.currentweather.CurrentWeatherCurrent
import com.dellosaneil.feature.model.currentweather.CurrentWeatherData
import com.dellosaneil.feature.model.currentweather.WeatherCondition

val CurrentWeatherDataSchema.toData
    get() = CurrentWeatherData(
        current = current.run {
            CurrentWeatherCurrent(
                apparentTemperature = apparentTemperature,
                isDay = isDay == 1,
                precipitation = precipitation,
                temperature2m = temperature2m,
                time = time * 1000L,
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
