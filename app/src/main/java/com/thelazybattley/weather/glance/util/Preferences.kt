package com.thelazybattley.weather.glance.util

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.thelazybattley.common.model.hourlyforecast.HourlyForecastData
import java.time.ZoneId

const val KEY_FORECASTED_WEATHER = "weather_forecast"
const val KEY_ADDRESS = "address"
const val KEY_IS_LOADING = "is_loading"
const val EMPTY_ADDRESS = "No Address"

fun Preferences.getAddress(): String {
    return this[stringPreferencesKey(KEY_ADDRESS)] ?: EMPTY_ADDRESS
}

fun MutablePreferences.setAddress(address: String) {
    this[stringPreferencesKey(KEY_ADDRESS)] = address
}

fun Preferences.getForecastedWeather(): HourlyForecastData {
    val jsonString =
        this[stringPreferencesKey(KEY_FORECASTED_WEATHER)] ?: return HourlyForecastData(
            hourly = emptyList(),
            timeZone = ZoneId.systemDefault().id
        )
    return Gson().fromJson(jsonString, HourlyForecastData::class.java)
}

fun MutablePreferences.setForecastedWeather(forecast: HourlyForecastData) {
    val jsonStr = Gson().toJson(forecast).toString()
    this[stringPreferencesKey(KEY_FORECASTED_WEATHER)] = jsonStr
}

fun Preferences.getIsLoading(): Boolean {
    return this[booleanPreferencesKey(KEY_IS_LOADING)] ?: true
}

fun MutablePreferences.setIsLoading(isLoading: Boolean) {
    this[booleanPreferencesKey(KEY_IS_LOADING)] = isLoading
}

