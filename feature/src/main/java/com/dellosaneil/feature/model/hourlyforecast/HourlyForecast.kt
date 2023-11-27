package com.dellosaneil.feature.model.hourlyforecast

data class HourlyForecast(
    val dateTimeMillis: Long,
    val main: HourlyForecastMain,
    val probabilityOfRain: Double,
    val weather: List<HourlyForecastWeather>,
    val wind: HourlyForecastWind
)
