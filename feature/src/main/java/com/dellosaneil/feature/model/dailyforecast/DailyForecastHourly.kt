package com.dellosaneil.feature.model.dailyforecast

import com.dellosaneil.feature.util.WeatherIconEnum

data class DailyForecastHourly(
    val tempC: Double,
    val dateTimeMillis: Long,
    val icon: WeatherIconEnum,
    val probabilityOfPrecipitation: Double
)
