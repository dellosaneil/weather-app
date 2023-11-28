package com.dellosaneil.feature.mapper

import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastDataSchema
import com.dellosaneil.feature.model.dailyforecast.DailyForecast
import com.dellosaneil.feature.model.dailyforecast.DailyForecastHourly
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecast
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastData
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastMain
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastWeather
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastWind
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.WeatherIconEnum
import com.dellosaneil.feature.util.epochToMillis
import com.dellosaneil.feature.util.kelvinToCelsius
import com.dellosaneil.feature.util.roundTwoDecimal
import com.dellosaneil.feature.util.toDateString

val HourlyForecastDataSchema.toData
    get() = HourlyForecastData(
        list = run {
            val currentDate =
                list.first().dt.epochToMillis.toDateString(pattern = DatePattern.DATE_MONTH)
            list
                .filter {
                    it.dt.epochToMillis.toDateString(pattern = DatePattern.DATE_MONTH) == currentDate
                }
                .map { forecast ->

                    HourlyForecast(
                        dateTimeMillis = forecast.dt.epochToMillis,
                        probabilityOfRain = (forecast.pop * 100.0).roundTwoDecimal,
                        main = HourlyForecastMain(
                            feelsLikeC = forecast.main.feelsLike.kelvinToCelsius,
                            humidity = forecast.main.humidity,
                            tempC = forecast.main.temp.kelvinToCelsius,
                            tempMinC = forecast.main.tempMin.kelvinToCelsius,
                            tempMaxC = forecast.main.tempMax.kelvinToCelsius,
                            pressure = forecast.main.pressure
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
                        },
                        visibility = forecast.visibility
                    )
                }
        }
    )

val HourlyForecastDataSchema.toDailyForecast
    get() = run {
        val groupedByDate =
            list.groupBy { it.dt.epochToMillis.toDateString(pattern = DatePattern.DAY_SHORT) }

        groupedByDate.map { (day, list) ->
            DailyForecast(
                highestTempC = list.maxOf { it.main.tempMax.kelvinToCelsius },
                lowestTempC = list.minOf { it.main.tempMin.kelvinToCelsius },
                icon = WeatherIconEnum.toWeatherIcon(icon = list.first().weather.first().icon),
                hourly = list.map { forecast ->
                    DailyForecastHourly(
                        tempC = forecast.main.temp.kelvinToCelsius,
                        dateTimeMillis = forecast.dt.epochToMillis,
                        icon = WeatherIconEnum.toWeatherIcon(icon = forecast.weather.first().icon)
                    )
                },
                day = day
            )
        }
    }
