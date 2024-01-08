package com.thelazybattley.current.model.dailyforecast

data class DailyForecastData(
    val daily: List<DailyForecastDaily>,
    val timeZone: String
) {
    companion object {
        fun dummyData() = DailyForecastData(
            daily = listOf(
                DailyForecastDaily.dummyData()
            ),
            timeZone = "GMT+8"
        )
    }
}
