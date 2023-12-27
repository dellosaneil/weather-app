package com.thelazybattley.feature.model.hourlyforecast

data class HourlyForecastData(
    val hourly: List<HourlyForecastHourly>,
) {
    companion object {
        fun dummyData() = HourlyForecastData(
            hourly = listOf(
                HourlyForecastHourly.dummyData()
            )
        )
    }
}
