package com.dellosaneil.feature.model.hourlyforecast

import com.dellosaneil.feature.model.currentweather.WeatherCondition

data class HourlyForecastHourly(
    val precipitationProbability: Int,
    val temperature2m: Double,
    val timeMillis: Long,
    val weatherCondition: WeatherCondition,
    val relativeHumidity2m: Int,
    val apparentTemperature: Double,
    val visibility: Int,
    val cloudCover: Int,
    val surfacePressure: Double,
    val windSpeed10m: Double,
    val windDirection10m: Int
) {
    companion object {
        fun dummyData() = HourlyForecastHourly(
            precipitationProbability = 32,
            temperature2m = 22.4,
            timeMillis = 1703150265628L,
            weatherCondition = WeatherCondition.toWeatherCondition(id = 1, isDay = true),
            relativeHumidity2m = 33,
            apparentTemperature = 25.2,
            visibility = 32412,
            cloudCover = 32,
            surfacePressure = 22.3,
            windSpeed10m = 32.2,
            windDirection10m = 33,
        )
    }
}
