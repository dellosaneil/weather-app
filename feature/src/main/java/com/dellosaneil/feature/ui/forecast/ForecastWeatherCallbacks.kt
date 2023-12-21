package com.dellosaneil.feature.ui.forecast

import com.dellosaneil.feature.model.dailyforecast.DailyForecastDaily

interface ForecastWeatherCallbacks {

    fun daySelected(
        dailyForecast: DailyForecastDaily
    )
}
