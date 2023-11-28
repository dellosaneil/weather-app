package com.dellosaneil.domain.mapper

import com.dellosaneil.domain.network.schema.current.CurrentWeatherDataSchema
import com.dellosaneil.domain.network.schema.current.CurrentWeatherMainSchema
import com.dellosaneil.domain.network.schema.current.CurrentWeatherSchema
import com.dellosaneil.domain.network.schema.current.CurrentWeatherSysSchema
import com.dellosaneil.domain.network.schema.current.CurrentWeatherWindSchema
import com.thelazybattley.data.network.response.currentweather.CurrentWeatherDataResponse

val CurrentWeatherDataResponse.toSchema
    get() = CurrentWeatherDataSchema(
        dt = dt,
        main = CurrentWeatherMainSchema(
            feelsLike = main.feelsLike,
            humidity = main.humidity,
            pressure = main.pressure,
            temp = main.temp,
            tempMax = main.tempMax,
            tempMin = main.tempMin
        ),
        name = name,
        sys = CurrentWeatherSysSchema(
            country = sys.country,
            id = sys.id,
            sunrise = sys.sunrise,
            sunset = sys.sunset
        ),
        weather = weather.map {
            CurrentWeatherSchema(
                description = it.description,
                icon = it.icon
            )
        },
        wind = CurrentWeatherWindSchema(
            deg = wind.deg,
            speed = wind.speed
        ),
        visibility = visibility
    )
