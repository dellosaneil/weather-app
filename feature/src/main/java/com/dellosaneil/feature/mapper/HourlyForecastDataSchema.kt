package com.dellosaneil.feature.mapper

import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastDataSchema
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecast
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastData
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastMain
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastWeather
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastWind
import com.dellosaneil.feature.util.WeatherIconEnum
import com.dellosaneil.feature.util.epochToMillis
import com.dellosaneil.feature.util.kelvinToCelsius
import com.dellosaneil.feature.util.roundTwoDecimal

val HourlyForecastDataSchema.toData
    get() = HourlyForecastData(
        list = list.map { forecast ->
            HourlyForecast(
                dateTimeMillis = forecast.dt.epochToMillis,
                probabilityOfRain = (forecast.pop * 100.0).roundTwoDecimal,
                main = HourlyForecastMain(
                    feelsLikeC = forecast.main.feelsLike.kelvinToCelsius,
                    humidity = forecast.main.humidity,
                    tempC = forecast.main.temp.kelvinToCelsius,
                    tempMinC = forecast.main.tempMin.kelvinToCelsius,
                    tempMaxC = forecast.main.tempMax.kelvinToCelsius
                ),
                wind = HourlyForecastWind(
                    deg = forecast.wind.deg,
                    speed = forecast.wind.speed
                ),
                weather = forecast.weather.map { weather ->
                    HourlyForecastWeather(
                        description = weather.description,
                        weatherIconEnum = WeatherIconEnum.toWeatherIcon(icon = weather.icon)
                    )
                }
            )
        }.take(8)
    )
