package com.dellosaneil.feature.ui.forecast

import com.dellosaneil.feature.model.dailyforecast.DailyForecast

interface ForecastWeatherCallbacks {

    fun daySelected(
        dailyForecast: DailyForecast
    )
}
