package com.dellosaneil.feature.ui.today

interface TodayWeatherCallbacks {
    fun fetchCurrentWeather(latitude: String, longitude: String)
    fun fetchHourlyForecast(latitude: String, longitude: String)
}
