package com.dellosaneil.feature.mapper

import com.dellosaneil.domain.network.schema.current.CurrentWeatherDataSchema
import com.dellosaneil.feature.model.currentweather.CurrentWeatherData
import com.dellosaneil.feature.model.currentweather.CurrentWeatherMain
import com.dellosaneil.feature.model.currentweather.CurrentWeatherSys
import com.dellosaneil.feature.model.currentweather.CurrentWeatherWeather
import com.dellosaneil.feature.model.currentweather.CurrentWeatherWind
import com.dellosaneil.feature.util.WeatherIconEnum
import com.dellosaneil.feature.util.epochToMillis
import com.dellosaneil.feature.util.kelvinToCelsius

val CurrentWeatherDataSchema.toData
    get() = CurrentWeatherData(
        currentTimeMillis = dt.epochToMillis,
        main = CurrentWeatherMain(
            feelsLikeC = main.feelsLike.kelvinToCelsius,
            humidity = main.humidity,
            pressure = main.pressure,
            tempC = main.temp.kelvinToCelsius,
            tempMaxC = main.tempMax.kelvinToCelsius,
            tempMinC = main.tempMin.kelvinToCelsius
        ),
        name = name,
        sys = CurrentWeatherSys(
            country = sys.country,
            id = sys.id,
            sunriseMillis = sys.sunrise.epochToMillis,
            sunsetMillis = sys.sunset.epochToMillis
        ),
        weather = weather.map {
            CurrentWeatherWeather(
                weatherIconEnum = WeatherIconEnum.toWeatherIcon(icon = it.icon),
                description = it.description
            )
        },
        wind = CurrentWeatherWind(
            deg = wind.deg,
            speed = wind.speed
        )
    )
