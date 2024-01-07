package com.thelazybattley.feature.glance.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.thelazybattley.domain.repository.WeatherRepository
import com.thelazybattley.feature.glance.ui.CurrentWeatherWidgetScreen
import com.thelazybattley.feature.mapper.toData
import com.thelazybattley.feature.mapper.today
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull

class WeatherWidget : GlanceAppWidget() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WeatherProviderEntryPoint {
        fun repository(): WeatherRepository
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext ?: throw IllegalStateException()
        val repositoryEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                WeatherProviderEntryPoint::class.java,
            )
        val repository = repositoryEntryPoint.repository()
        val userLocation = repository.getLocation().firstOrNull() ?: return
        val data = repository.getHourlyForecast(
            latitude = userLocation.latitude.toString(),
            longitude = userLocation.longitude.toString()
        ).getOrNull()?.toData ?: return
        val forecast = data.copy(
            hourly = data.hourly.today(timeZone = data.timeZone).take(2)
        )
        provideContent {
            CurrentWeatherWidgetScreen(
                forecast = forecast,
                location = userLocation.address
            )
        }
    }

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition
}
