package com.thelazybattley.feature.model.currentweather

data class CurrentWeatherData(
    val current: CurrentWeatherCurrent
) {
    companion object {
        fun dummyData() = CurrentWeatherData(
            current = CurrentWeatherCurrent.dummyData()
        )
    }
}