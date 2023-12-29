package com.thelazybattley.feature.model.currentweather

data class CurrentWeatherData(
    val current: CurrentWeatherCurrent,
    val timeZone: String
) {
    companion object {
        fun dummyData() = CurrentWeatherData(
            current = CurrentWeatherCurrent.dummyData(),
            timeZone = "GMT+8"
        )
    }
}
