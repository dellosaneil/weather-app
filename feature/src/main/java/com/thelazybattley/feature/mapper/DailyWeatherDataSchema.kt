package com.thelazybattley.feature.mapper

import com.thelazybattley.domain.network.schema.dailyforecast.DailyForecastDataSchema
import com.thelazybattley.feature.model.currentweather.WeatherCondition
import com.thelazybattley.feature.model.dailyforecast.DailyForecastDaily
import com.thelazybattley.feature.model.dailyforecast.DailyForecastData
import com.thelazybattley.feature.model.hourlyforecast.HourlyForecastHourly
import com.thelazybattley.feature.util.DatePattern
import com.thelazybattley.feature.util.epochToMillis
import com.thelazybattley.feature.util.toDateString
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun DailyForecastDataSchema.toData(
    hourlyForecast: List<HourlyForecastHourly>
) = DailyForecastData(
    timeZone = timeZone,
    daily = daily.run {
        val daily = mutableListOf<DailyForecastDaily>()
        repeat(daylightDuration.size) { index ->
            val millis = time[index].epochToMillis
            val filteredHourlyForecast = hourlyForecast.filter {
                it.timeMillis.toDateString(
                    pattern = DatePattern.DATE_MONTH,
                    timeZone = timeZone
                ) == millis.toDateString(
                    pattern = DatePattern.DATE_MONTH,
                    timeZone = timeZone
                )
            }
            DailyForecastDaily(
                daylightDuration = daylightDuration[index].seconds.toString(DurationUnit.HOURS),
                precipitationProbabilityMax = precipitationProbabilityMax[index],
                sunrise = sunrise[index].epochToMillis.toDateString(
                    pattern = DatePattern.HOUR_MINUTES_MERIDIEM,
                    timeZone = timeZone
                ),
                sunset = sunset[index].epochToMillis.toDateString(
                    pattern = DatePattern.HOUR_MINUTES_MERIDIEM,
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

