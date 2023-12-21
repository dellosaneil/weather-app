package com.dellosaneil.feature.model.dailyforecast

data class DailyForecastData(
    val daily: List<DailyForecastDaily>
) {
    companion object {
        fun dummyData() = DailyForecastData(
            daily = listOf(
                DailyForecastDaily.dummyData()
            )
        )
    }
}
