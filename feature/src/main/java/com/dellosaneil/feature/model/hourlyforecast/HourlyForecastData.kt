package com.dellosaneil.feature.model.hourlyforecast

import com.dellosaneil.feature.util.WeatherIconEnum

data class HourlyForecastData(
    val list: List<HourlyForecast>
) {
    companion object {
        fun dummyData() = HourlyForecastData(
            list = listOf(
                HourlyForecast(
                    dateTimeMillis = 1701183600000L,
                    probabilityOfRain = 83.2,
                    main = HourlyForecastMain(
                        feelsLikeC = 21.3,
                        humidity = 33,
                        tempC = 32.1,
                        tempMaxC = 33.3,
                        tempMinC = 11.3
                    ),
                    weather = listOf(
                        HourlyForecastWeather(
                            description = "Thunderstorm",
                            weatherIconEnum = WeatherIconEnum.THUNDERSTORM
                        )
                    ),
                    wind = HourlyForecastWind(
                        deg = 32, speed = 43.3
                    )
                ),
                HourlyForecast(
                    dateTimeMillis = 1701183600000L,
                    probabilityOfRain = 83.2,
                    main = HourlyForecastMain(
                        feelsLikeC = 21.3,
                        humidity = 33,
                        tempC = 32.1,
                        tempMaxC = 33.3,
                        tempMinC = 11.3
                    ),
                    weather = listOf(
                        HourlyForecastWeather(
                            description = "Thunderstorm",
                            weatherIconEnum = WeatherIconEnum.THUNDERSTORM
                        )
                    ),
                    wind = HourlyForecastWind(
                        deg = 32, speed = 43.3
                    )
                )
            )
        )
    }
}
