package com.thelazybattley.current.model.dailyforecast

import com.thelazybattley.common.model.WeatherCondition
import com.thelazybattley.common.model.hourlyforecast.HourlyForecastHourly

data class DailyForecastDaily(
    val daylightDuration: String,
    val precipitationProbabilityMax: Int,
    val sunrise: String,
    val sunset: String,
    val temperature2mMax: Double,
    val temperature2mMin: Double,
    val timeMillis: Long,
    val weatherCondition: WeatherCondition,
    val hourlyForecast: List<HourlyForecastHourly>,
    val maxPrecipitationQuantity: Double,
    val minPrecipitationQuantity: Double
) {
    companion object {
        fun dummyData() = run {
            val hourlyForecasts = listOf(
                HourlyForecastHourly.dummyData().copy(
                    precipitation = 2.3,
                    precipitationProbability = 32
                ),
                HourlyForecastHourly.dummyData().copy(
                    precipitationProbability = 25,
                    precipitation = 1.2
                ),
                HourlyForecastHourly.dummyData()
                    .copy(
                        precipitation = 5.6,
                        precipitationProbability = 87
                    ),
            )
            DailyForecastDaily(
                daylightDuration = "4 hrs",
                precipitationProbabilityMax = 32,
                sunrise = "5:13am",
                sunset = "6:00pm",
                temperature2mMax = 32.2,
                temperature2mMin = 22.3,
                timeMillis = 1703156210705L,
                weatherCondition = WeatherCondition.toWeatherCondition(id = 1, isDay = true),
                hourlyForecast = hourlyForecasts,
                maxPrecipitationQuantity = hourlyForecasts.maxOf { it.precipitation },
                minPrecipitationQuantity = hourlyForecasts.minOf { it.precipitation }
            )
        }
    }
}
