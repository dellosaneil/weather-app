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
import com.thelazybattley.common.mapper.toData
import com.thelazybattley.common.mapper.today
import com.thelazybattley.common.model.hourlyforecast.HourlyForecastData
import com.thelazybattley.domain.repository.WeatherRepository
import com.thelazybattley.weather.glance.util.setAddress
import com.thelazybattley.weather.glance.util.setForecastedWeather
import com.thelazybattley.weather.glance.util.setIsLoading
import com.thelazybattley.weather.glance.widget.ForecastedWeatherWidget
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import java.time.Duration

class ForecastedWeatherWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(appContext = context, params = params) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WeatherProviderEntryPoint {
        fun repository(): WeatherRepository
    }

    override suspend fun doWork(): Result {
        val appContext = context.applicationContext ?: throw IllegalStateException()
        updateIsLoadingWidget(isLoading = true)
        val repositoryEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                WeatherProviderEntryPoint::class.java,
            )

        val repository = repositoryEntryPoint.repository()

        val userLocation = repository.getLocation().firstOrNull() ?: run {
            updateIsLoadingWidget(isLoading = false)
            return Result.retry()
        }

        val schema = repository.getHourlyForecast(
            latitude = userLocation.latitude.toString(),
            longitude = userLocation.longitude.toString()
        ).getOrNull() ?: run {
            updateIsLoadingWidget(isLoading = false)
            return Result.retry()
        }

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

    private suspend fun updateIsLoadingWidget(isLoading: Boolean) {
        GlanceAppWidgetManager(context = context)
            .getGlanceIds(ForecastedWeatherWidget::class.java)
            .forEach { id ->
                updateAppWidgetState(context = context, glanceId = id) { prefs ->
                    prefs.setIsLoading(isLoading = isLoading)
                }
                ForecastedWeatherWidget().update(context = context, id = id)
            }
    }

    private suspend fun updateWidget(forecast: HourlyForecastData, address: String) {
        GlanceAppWidgetManager(context = context)
            .getGlanceIds(ForecastedWeatherWidget::class.java)
            .forEach { id ->
                updateAppWidgetState(context = context, glanceId = id) { prefs ->
                    prefs.setForecastedWeather(
                        forecast = forecast
                    )
                    prefs.setAddress(address = address)
                    prefs.setIsLoading(isLoading = false)
                }
                ForecastedWeatherWidget().update(context = context, id = id)
            }

    }

    companion object {
        private const val WORKER_NAME = "forecasted_weather_worker"

        fun startWeatherFetch(workManager: WorkManager) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(false)
                .build()

            val request =
                PeriodicWorkRequestBuilder<ForecastedWeatherWorker>(Duration.ofMinutes(15))
                    .setConstraints(constraints)
                    .build()

            workManager.enqueueUniquePeriodicWork(
                WORKER_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }

        fun cancelWeatherFetch(workManager: WorkManager) {
            workManager.cancelUniqueWork(WORKER_NAME)
        }
    }
}
