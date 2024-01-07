package com.thelazybattley.feature.glance.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.thelazybattley.feature.glance.ui.CurrentWeatherWidgetScreen

class WeatherWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            CurrentWeatherWidgetScreen()
        }
    }
}
