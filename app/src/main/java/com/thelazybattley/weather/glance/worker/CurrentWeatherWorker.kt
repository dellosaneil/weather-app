package com.thelazybattley.weather.glance.worker

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.thelazybattley.domain.repository.WeatherRepository
import com.thelazybattley.feature.mapper.toData
import com.thelazybattley.feature.mapper.today
import com.thelazybattley.feature.model.hourlyforecast.HourlyForecastData
import com.thelazybattley.weather.glance.utils.setAddress
import com.thelazybattley.weather.glance.utils.setForecastedWeather
import com.thelazybattley.weather.glance.widget.WeatherWidget
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import java.time.Duration

class CurrentWeatherWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(appContext = context, params = params) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WeatherProviderEntryPoint {
        fun repository(): WeatherRepository
    }

    override suspend fun doWork(): Result {
        val appContext = context.applicationContext ?: throw IllegalStateException()
        val repositoryEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                WeatherProviderEntryPoint::class.java,
            )

        val repository = repositoryEntryPoint.repository()

        val userLocation = repository.getLocation().firstOrNull() ?: return Result.retry()
        val schema = repository.getHourlyForecast(
            latitude = userLocation.latitude.toString(),
            longitude = userLocation.longitude.toString()
        ).getOrNull() ?: return Result.retry()
        updateWidget(
            forecast = schema.toData.copy(
                hourly = schema.toData.hourly.today(
                    timeZone = schema.timeZone
                ).take(3)
            ),
            address = userLocation.address
        )
        return Result.success()
    }

    private suspend fun updateWidget(forecast: HourlyForecastData, address: String) {
        GlanceAppWidgetManager(context = context)
            .getGlanceIds(WeatherWidget::class.java)
            .forEach { id ->
                updateAppWidgetState(context = context, glanceId = id) { prefs ->
                    prefs.setForecastedWeather(
                        forecast = forecast
                    )
                    prefs.setAddress(address = address)
                }
                WeatherWidget().update(context = context, id = id)
            }

    }

    companion object {
        fun startWeatherFetch(workManager: WorkManager) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(false)
                .build()

            val request = PeriodicWorkRequestBuilder<CurrentWeatherWorker>(Duration.ofMinutes(15))
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork(
                "worker",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }

        fun cancelWeatherFetch(workManager: WorkManager) {
            workManager.cancelUniqueWork("worker")
        }
    }
}
