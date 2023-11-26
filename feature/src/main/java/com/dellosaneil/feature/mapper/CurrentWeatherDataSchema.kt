package com.dellosaneil.feature.mapper

import com.dellosaneil.domain.network.schema.CurrentWeatherDataSchema
import com.dellosaneil.feature.model.currentweather.CurrentWeatherCondition
import com.dellosaneil.feature.model.currentweather.CurrentWeatherCurrent
import com.dellosaneil.feature.model.currentweather.CurrentWeatherData
import com.dellosaneil.feature.model.currentweather.CurrentWeatherLocation

val CurrentWeatherDataSchema.toData
    get() = CurrentWeatherData(
        current = CurrentWeatherCurrent(
            cloud = current.cloud,
            feelslikeC = current.feelslikeC,
            humidity = current.humidity,
            isDay = current.isDay != 0,
            lastUpdatedEpoch = current.lastUpdatedEpoch,
            lastUpdated = current.lastUpdated,
            precipMm = current.precipMm,
            precipIn = current.precipIn,
            windKph = current.windKph,
            tempC = current.tempC,
            condition = CurrentWeatherCondition(
                icon = "https:${current.condition.icon}",
                text = current.condition.text
            )
        ),
        location = CurrentWeatherLocation(
            country = location.country,
            localtimeEpoch = location.localtimeEpoch,
            name = location.name,
            region = location.region,
            tzId = location.tzId
        )
    )
