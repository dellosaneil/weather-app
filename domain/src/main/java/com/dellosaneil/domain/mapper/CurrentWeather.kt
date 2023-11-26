package com.dellosaneil.domain.mapper

import com.dellosaneil.domain.network.schema.CurrentWeatherConditionSchema
import com.dellosaneil.domain.network.schema.CurrentWeatherCurrentSchema
import com.dellosaneil.domain.network.schema.CurrentWeatherDataSchema
import com.dellosaneil.domain.network.schema.CurrentWeatherLocationSchema
import com.thelazybattley.data.network.response.current.CurrentWeatherDataResponse

val CurrentWeatherDataResponse.toSchema
    get() = CurrentWeatherDataSchema(
        current = CurrentWeatherCurrentSchema(
            cloud = current.cloud,
            feelslikeC = current.feelslikeC,
            humidity = current.humidity,
            isDay = current.isDay,
            lastUpdated = current.lastUpdated ?: "",
            lastUpdatedEpoch = current.lastUpdatedEpoch,
            precipIn = current.precipIn,
            precipMm = current.precipMm,
            tempC = current.tempC,
            windKph = current.windKph,
            condition = CurrentWeatherConditionSchema(
                icon = current.condition.icon,
                text = current.condition.text,
            )
        ),
        location = CurrentWeatherLocationSchema(
            country = location.country,
            localtimeEpoch = location.localtimeEpoch,
            name = location.name,
            region = location.region,
            tzId = location.tzId
        )
    )
