package com.dellosaneil.feature.mapper

import com.dellosaneil.domain.network.schema.hourlyforecast.HourlyForecastDataSchema
import com.dellosaneil.feature.model.currentweather.WeatherCondition
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastData
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastHourly
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.epochToMillis
import com.dellosaneil.feature.util.toDateString
import java.time.LocalTime

val HourlyForecastDataSchema.toData
    get() = HourlyForecastData(
        hourly = hourly.run {
            val hourly = mutableListOf<HourlyForecastHourly>()
            repeat(precipitationProbability.size) { index ->
                HourlyForecastHourly(
                    precipitationProbability = precipitationProbability[index],
                    temperature2m = temperature2m[index],
                    timeMillis = time[index].epochToMillis,
                    weatherCondition = run {
                        val hour =
                            time[index].epochToMillis.toDateString(pattern = DatePattern.HOURS).toInt()
                        val isDay = hour in 6..18
                        WeatherCondition.toWeatherCondition(id = weatherCode[index], isDay = isDay)
                    },
                    surfacePressure = surfacePressure[index],
                    visibility = visibility[index], cloudCover = cloudCover[index],
                    relativeHumidity2m = relativeHumidity2m[index],
                    apparentTemperature = apparentTemperature[index],
                    windSpeed10m = windSpeed10m[index],
                    windDirection10m = windDirection10m[index]
                ).also {
                    hourly.add(it)
                }
            }
            hourly
        }
    )

val List<HourlyForecastHourly>.today
    get() = run {
        subList(fromIndex = LocalTime.now().hour, toIndex = 24)
    }

val HourlyForecastData.today
    get() = run {
        val startIndex = LocalTime.now().hour
        copy(
            hourly = hourly.subList(fromIndex = startIndex, toIndex = 24)
        )
    }
