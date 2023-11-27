package com.dellosaneil.feature.model.currentweather

import com.dellosaneil.feature.util.WeatherIconEnum

data class CurrentWeatherData(
    val currentTimeMillis: Long,
    val main: CurrentWeatherMain,
    val name: String,
    val sys: CurrentWeatherSys,
    val weather: List<CurrentWeatherWeather>,
    val wind: CurrentWeatherWind
) {
    companion object {
        fun dummyData() = CurrentWeatherData(
            currentTimeMillis = System.currentTimeMillis(),
            main = CurrentWeatherMain(
                feelsLikeC = 32.1,
                humidity = 3,
                pressure = 32,
                tempC = 31.2,
                tempMaxC = 44.2,
                tempMinC = 21.0,
            ),
            name = "Davao City",
            sys = CurrentWeatherSys(
                country = "Philippines",
                id = 32,
                sunsetMillis = 1700990257000L,
                sunriseMillis = 1700947925000L,
            ),
            weather = listOf(
                CurrentWeatherWeather(
                    description = "Sunny",
                    weatherIconEnum = WeatherIconEnum.SUNNY
                )
            ),
            wind = CurrentWeatherWind(
                deg = 360,
                speed = 3.09
            )
        )
    }
}
