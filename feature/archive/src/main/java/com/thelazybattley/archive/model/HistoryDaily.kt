package com.thelazybattley.archive.model

import com.thelazybattley.common.model.WeatherCondition

data class HistoryDaily(
    val precipitationHours: List<Double>,
    val precipitationSum: List<Double>,
    val temperature2mMax: List<Double>,
    val temperature2mMean: List<Double>,
    val temperature2mMin: List<Double>,
    val time: List<Long>,
    val weatherCode: List<WeatherCondition>
) {
    companion object {
        fun createDummy() = HistoryDaily(
            precipitationSum = listOf(
                2.3, 0.0, 3.3
            ),
            temperature2mMax = listOf(
                24.3, 33.3, 26.4
            ),
            temperature2mMean = listOf(
                24.3, 33.3, 26.4
            ),
            temperature2mMin = listOf(
                24.3, 33.3, 26.4
            ),
            precipitationHours = listOf(
                3.2, 2.1, 1.0
            ),
            time = listOf(
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
            ),
            weatherCode = listOf(
                WeatherCondition.toWeatherCondition(id = 3, isDay = true),
                WeatherCondition.toWeatherCondition(id = 3, isDay = true),
                WeatherCondition.toWeatherCondition(id = 3, isDay = true),
            )
        )
    }
}
