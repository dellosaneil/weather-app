package com.thelazybattley.weather.glance.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.WorkManager
import com.thelazybattley.weather.glance.worker.CurrentWeatherWorker

class WeatherWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WeatherWidget()

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        CurrentWeatherWorker.startWeatherFetch(WorkManager.getInstance(context))
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        CurrentWeatherWorker.cancelWeatherFetch(workManager = WorkManager.getInstance(context!!))
    }
}
