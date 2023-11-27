package com.dellosaneil.domain.mapper

import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastCitySchema
import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastDataSchema
import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastMainSchema
import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastSchema
import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastWeatherSchema
import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastWindSchema
import com.thelazybattley.data.network.response.hourlyforecast.HourlyForecastDataResponse

val HourlyForecastDataResponse.toSchema
    get() = HourlyForecastDataSchema(
        city = HourlyForecastCitySchema(
            country = city.country,
            name = city.name
        ),
        list = list.map { forecast ->
            HourlyForecastSchema(
                dt = forecast.dt,
                pop = forecast.pop,
                weather = forecast.weather.map { weather ->
                    HourlyForecastWeatherSchema(
                        description = weather.description,
                        icon = weather.icon
                    )
                },
                wind = HourlyForecastWindSchema(
                    deg = forecast.wind.deg,
                    speed = forecast.wind.speed
                ),
                main = HourlyForecastMainSchema(
                    feelsLike = forecast.main.feelsLike,
                    humidity = forecast.main.humidity,
                    temp = forecast.main.temp,
                    tempMax = forecast.main.tempMax,
                    tempMin = forecast.main.tempMin
                )
            )
        }
    )
