package com.thelazybattley.forecast.ui

import com.thelazybattley.current.model.dailyforecast.DailyForecastDaily


interface ForecastWeatherCallbacks {

    fun daySelected(
        dailyForecast: DailyForecastDaily
    )
}
