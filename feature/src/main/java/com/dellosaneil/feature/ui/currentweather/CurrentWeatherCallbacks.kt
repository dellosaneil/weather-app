package com.dellosaneil.feature.ui.currentweather

interface CurrentWeatherCallbacks {
    fun fetchCurrentWeather(latitude: String, longitude: String)
}
