package com.thelazybattley.feature.ui.forecast

import com.thelazybattley.feature.model.dailyforecast.DailyForecastDaily

interface ForecastWeatherCallbacks {

    fun daySelected(
        dailyForecast: DailyForecastDaily
    )
}
