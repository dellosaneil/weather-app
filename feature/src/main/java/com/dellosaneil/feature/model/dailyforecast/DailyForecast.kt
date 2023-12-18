package com.dellosaneil.feature.model.dailyforecast

import com.dellosaneil.feature.util.WeatherIconEnum

data class DailyForecast(
    val highestTempC: Double,
    val lowestTempC: Double,
    val icon: WeatherIconEnum,
    val hourly: List<DailyForecastHourly>,
    val day: String,
    val temperatures: List<Double>,
    val timeStamp: List<String>
) {
    companion object {
        fun dummyData() = DailyForecast(
            highestTempC = 33.0,
            lowestTempC = 26.0,
            icon = WeatherIconEnum.THUNDERSTORM,
            hourly = listOf(
                DailyForecastHourly(
                    tempC = 33.0,
                    icon = WeatherIconEnum.MIST_SUN,
                    dateTimeMillis = 1701075282549L
                ),
                DailyForecastHourly(
                    tempC = 32.3,
                    icon = WeatherIconEnum.MIST_SUN,
                    dateTimeMillis = 1701075282549L
                ),
                DailyForecastHourly(
                    tempC = 31.0,
                    icon = WeatherIconEnum.MIST_SUN,
                    dateTimeMillis = 1701075282549L
                ),
                DailyForecastHourly(
                    tempC = 26.0,
                    icon = WeatherIconEnum.MIST_SUN,
                    dateTimeMillis = 1701075282549L
                )
            ),
            day = "Wednesday",
            temperatures = listOf(
                33.0,
                32.3,
                31.0,
                26.0
            ),
            timeStamp = listOf("1:00am", "4:00am", "7:00am", "10:00am")
        )
    }
}
