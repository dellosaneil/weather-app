package com.thelazybattley.feature.model.currentweather

import com.thelazybattley.feature.R

data class CurrentWeatherCurrent(
    val apparentTemperature: Double,
    val isDay: Boolean,
    val precipitation: Double,
    val temperature2m: Double,
    val time: Long,
    val windDirection10m: Int,
    val windSpeed10m: Double,
    val cloudCover: Int,
    val surfacePressure: Double,
    val relativeHumidity2m: Int,
    val weatherCondition: WeatherCondition
) {
    companion object {
        fun dummyData() = CurrentWeatherCurrent(
            apparentTemperature = 32.3,
            isDay = true,
            precipitation = 10.3,
            temperature2m = 31.3,
            time = 1703131379685L,
            windDirection10m = 300,
            windSpeed10m = 32.3,
            cloudCover = 33,
            surfacePressure = 345.2,
            relativeHumidity2m = 30,
            weatherCondition = WeatherCondition(
                icon = R.drawable.img_light_rain,
                description = "light rain"
            )
        )
    }
}
