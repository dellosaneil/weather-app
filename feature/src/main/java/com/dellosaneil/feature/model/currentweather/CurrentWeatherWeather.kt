package com.dellosaneil.feature.model.currentweather

import com.dellosaneil.feature.util.WeatherIconEnum

data class CurrentWeatherWeather(
    val description: String,
    val weatherIconEnum : WeatherIconEnum
)
