package com.dellosaneil.feature.mapper

import com.dellosaneil.domain.network.schema.dailyforecast.DailyForecastDataSchema
import com.dellosaneil.feature.model.currentweather.WeatherCondition
import com.dellosaneil.feature.model.dailyforecast.DailyForecastDaily
import com.dellosaneil.feature.model.dailyforecast.DailyForecastData
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastHourly
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.epochToMillis
import com.dellosaneil.feature.util.toDateString
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun DailyForecastDataSchema.toData(
    hourlyForecastHourly: List<HourlyForecastHourly>
) = DailyForecastData(
    daily = daily.run {
        val daily = mutableListOf<DailyForecastDaily>()
        repeat(daylightDuration.size) { index ->
            val millis = time[index].epochToMillis
            val filteredHourlyForecast =  hourlyForecastHourly.filter {
                it.timeMillis.toDateString(pattern = DatePattern.DATE_MONTH) == millis.toDateString(
                    pattern = DatePattern.DATE_MONTH
                )
            }
            DailyForecastDaily(
                daylightDuration = daylightDuration[index].seconds.toString(DurationUnit.HOURS),
                precipitationProbabilityMax = precipitationProbabilityMax[index],
                sunrise = sunrise[index].epochToMillis.toDateString(pattern = DatePattern.HOUR_MINUTES_MERIDIEM),
                sunset = sunset[index].epochToMillis.toDateString(pattern = DatePattern.HOUR_MINUTES_MERIDIEM),
                temperature2mMax = temperature2mMax[index],
                temperature2mMin = temperature2mMin[index],
                timeMillis = time[index].epochToMillis,
                weatherCondition = run {
                    WeatherCondition.toWeatherCondition(id = weatherCode[index], isDay = true)
                },
                hourlyForecast = filteredHourlyForecast,
                minPrecipitationQuantity = filteredHourlyForecast.minOfOrNull { it.precipitation } ?: 0.0,
                maxPrecipitationQuantity = filteredHourlyForecast.maxOfOrNull { it.precipitation } ?: 0.0,
            ).also {
                daily.add(it)
            }
        }
        daily
    }
)

