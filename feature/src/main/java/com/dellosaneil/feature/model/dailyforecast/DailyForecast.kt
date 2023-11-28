package com.dellosaneil.feature.model.dailyforecast

import com.dellosaneil.feature.util.WeatherIconEnum

data class DailyForecast(
    val highestTempC: Double,
    val lowestTempC: Double,
    val icon: WeatherIconEnum,
    val hourly: List<DailyForecastHourly>,
    val day: String,
) {
    companion object {
        fun dummyData() = DailyForecast(
            highestTempC = 32.2,
            lowestTempC = 13.4,
            icon = WeatherIconEnum.THUNDERSTORM,
            hourly = listOf(
                DailyForecastHourly(
                    tempC = 33.6,
                    icon = WeatherIconEnum.MIST_SUN,
                    dateTimeMillis = 1701075282549L
                ),
                DailyForecastHourly(
                    tempC = 33.6,
                    icon = WeatherIconEnum.MIST_SUN,
                    dateTimeMillis = 1701075282549L
                ),
                DailyForecastHourly(
                    tempC = 33.6,
                    icon = WeatherIconEnum.MIST_SUN,
                    dateTimeMillis = 1701075282549L
                )
            ),
            day = "Wednesday"
        )
    }
}
