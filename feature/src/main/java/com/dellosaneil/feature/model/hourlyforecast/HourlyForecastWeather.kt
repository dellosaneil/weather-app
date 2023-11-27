package com.dellosaneil.feature.model.hourlyforecast

import com.dellosaneil.feature.util.WeatherIconEnum

data class HourlyForecastWeather(
    val description: String,
    val weatherIconEnum: WeatherIconEnum
)
