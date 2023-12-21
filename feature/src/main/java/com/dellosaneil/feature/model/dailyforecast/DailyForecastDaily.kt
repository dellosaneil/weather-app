package com.dellosaneil.feature.model.dailyforecast

import com.dellosaneil.feature.model.currentweather.WeatherCondition
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastHourly

data class DailyForecastDaily(
    val daylightDuration: String,
    val precipitationProbabilityMax: Int,
    val sunrise: String,
    val sunset: String,
    val temperature2mMax: Double,
    val temperature2mMin: Double,
    val timeMillis: Long,
    val weatherCondition: WeatherCondition,
    val hourlyForecast: List<HourlyForecastHourly>
) {
    companion object {
        fun dummyData() = DailyForecastDaily(
            daylightDuration = "4 hrs",
            precipitationProbabilityMax = 32,
            sunrise = "5:13am",
            sunset = "6:00pm",
            temperature2mMax = 32.2,
            temperature2mMin = 22.3,
            timeMillis = 1703156210705L,
            weatherCondition = WeatherCondition.toWeatherCondition(id = 1, isDay = true),
            hourlyForecast = listOf(
                HourlyForecastHourly.dummyData(),
                HourlyForecastHourly.dummyData(),
                HourlyForecastHourly.dummyData(),
            )
        )
    }
}
