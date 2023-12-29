package com.thelazybattley.feature.mapper

import com.thelazybattley.domain.network.schema.hourlyforecast.HourlyForecastDataSchema
import com.thelazybattley.feature.model.currentweather.WeatherCondition
import com.thelazybattley.feature.model.hourlyforecast.HourlyForecastData
import com.thelazybattley.feature.model.hourlyforecast.HourlyForecastHourly
import com.thelazybattley.feature.util.DatePattern
import com.thelazybattley.feature.util.epochToMillis
import com.thelazybattley.feature.util.toDateString
import java.time.LocalTime
import java.time.ZoneId

val HourlyForecastDataSchema.toData
    get() = HourlyForecastData(
        timeZone = timeZone,
        hourly = hourly.run {
            val hourly = mutableListOf<HourlyForecastHourly>()
            repeat(precipitationProbability.size) { index ->
                HourlyForecastHourly(
                    precipitationProbability = precipitationProbability[index],
                    temperature2m = temperature2m[index],
                    timeMillis = time[index].epochToMillis,
                    weatherCondition = run {
                        val hour =
                            time[index].epochToMillis.toDateString(
                                pattern = DatePattern.HOURS,
                                timeZone = timeZone
                            ).toInt()
                        val isDay = hour in 6..18
                        WeatherCondition.toWeatherCondition(id = weatherCode[index], isDay = isDay)
                    },
                    surfacePressure = surfacePressure[index],
                    visibility = visibility[index], cloudCover = cloudCover[index],
                    relativeHumidity2m = relativeHumidity2m[index],
                    apparentTemperature = apparentTemperature[index],
                    windSpeed10m = windSpeed10m[index],
                    windDirection10m = windDirection10m[index],
                    precipitation = precipitation[index]
                ).also {
                    hourly.add(it)
                }
            }
            hourly
        }
    )

fun List<HourlyForecastHourly>.today(timeZone: String): List<HourlyForecastHourly> {
    return subList(fromIndex = LocalTime.now(ZoneId.of(timeZone)).hour, toIndex = 24)
}

fun HourlyForecastData.today(timeZone: String): HourlyForecastData {
    val startIndex = LocalTime.now(ZoneId.of(timeZone)).hour
    return copy(
        hourly = hourly.subList(fromIndex = startIndex, toIndex = 24)
    )
}
