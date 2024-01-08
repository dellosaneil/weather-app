package com.thelazybattley.current.mapper

import com.thelazybattley.common.model.WeatherCondition
import com.thelazybattley.common.model.hourlyforecast.HourlyForecastHourly
import com.thelazybattley.common.util.epochToMillis
import com.thelazybattley.common.util.toDateString
import com.thelazybattley.domain.network.schema.dailyforecast.DailyForecastDataSchema
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun DailyForecastDataSchema.toData(
    hourlyForecast: List<HourlyForecastHourly>
) = com.thelazybattley.current.model.dailyforecast.DailyForecastData(
    timeZone = timeZone,
    daily = daily.run {
        val daily =
            mutableListOf<com.thelazybattley.current.model.dailyforecast.DailyForecastDaily>()
        repeat(daylightDuration.size) { index ->
            val millis = time[index].epochToMillis
            val filteredHourlyForecast = hourlyForecast.filter {
                it.timeMillis.toDateString(
                    pattern = com.thelazybattley.common.util.DatePattern.DATE_MONTH,
                    timeZone = timeZone
                ) == millis.toDateString(
                    pattern = com.thelazybattley.common.util.DatePattern.DATE_MONTH,
                    timeZone = timeZone
                )
            }
            com.thelazybattley.current.model.dailyforecast.DailyForecastDaily(
                daylightDuration = daylightDuration[index].seconds.toString(DurationUnit.HOURS),
                precipitationProbabilityMax = precipitationProbabilityMax[index],
                sunrise = sunrise[index].epochToMillis.toDateString(
                    pattern = com.thelazybattley.common.util.DatePattern.HOUR_MINUTES_MERIDIEM,
                    timeZone = timeZone
                ),
                sunset = sunset[index].epochToMillis.toDateString(
                    pattern = com.thelazybattley.common.util.DatePattern.HOUR_MINUTES_MERIDIEM,
                    timeZone = timeZone
                ),
                temperature2mMax = temperature2mMax[index],
                temperature2mMin = temperature2mMin[index],
                timeMillis = time[index].epochToMillis,
                weatherCondition = WeatherCondition.toWeatherCondition(
                    id = weatherCode[index],
                    isDay = true
                ),
                hourlyForecast = filteredHourlyForecast,
                minPrecipitationQuantity = filteredHourlyForecast.minOfOrNull { it.precipitation }
                    ?: 0.0,
                maxPrecipitationQuantity = filteredHourlyForecast.maxOfOrNull { it.precipitation }
                    ?: 0.0,
            ).also {
                daily.add(it)
            }
        }
        daily
    }
)

