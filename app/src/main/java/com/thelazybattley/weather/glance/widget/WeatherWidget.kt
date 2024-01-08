package com.thelazybattley.weather.glance.widget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.thelazybattley.weather.glance.ui.CurrentWeatherWidgetScreen
import com.thelazybattley.weather.glance.utils.getAddress
import com.thelazybattley.weather.glance.utils.getForecastedWeather

class WeatherWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            val forecastedWeather = prefs.getForecastedWeather()
            val address = prefs.getAddress()
            CurrentWeatherWidgetScreen(
                forecast = forecastedWeather,
                location = address,
                context = context,
            )
        }
    }
}
