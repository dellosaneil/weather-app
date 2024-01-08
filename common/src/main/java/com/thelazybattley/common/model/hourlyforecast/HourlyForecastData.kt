package com.thelazybattley.common.model.hourlyforecast

data class HourlyForecastData(
    val hourly: List<HourlyForecastHourly>,
    val timeZone: String,
) {
    companion object {
        fun dummyData() = HourlyForecastData(
            hourly = listOf(
                HourlyForecastHourly.dummyData()
            ),
            timeZone = "GMT+8"
        )
    }
}
