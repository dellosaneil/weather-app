package com.thelazybattley.weather.glance.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.WorkManager
import com.thelazybattley.weather.glance.worker.ForecastedWeatherWorker

class ForecastedWeatherWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ForecastedWeatherWidget()

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        ForecastedWeatherWorker.startWeatherFetch(WorkManager.getInstance(context))
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        ForecastedWeatherWorker.cancelWeatherFetch(workManager = WorkManager.getInstance(context!!))
    }
}
