package com.thelazybattley.feature.glance.ui

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.layout.wrapContentWidth
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.thelazybattley.feature.R
import com.thelazybattley.feature.model.hourlyforecast.HourlyForecastData
import com.thelazybattley.feature.util.Colors
import com.thelazybattley.feature.util.DatePattern
import com.thelazybattley.feature.util.toCelcius
import com.thelazybattley.feature.util.toDateString

@Composable
fun CurrentWeatherWidgetScreen(forecast: HourlyForecastData, location: String) {
    val currentWeather = forecast.hourly.firstOrNull() ?: return

    Column(
        modifier = GlanceModifier
            .padding(all = 16.dp)
            .background(
                ImageProvider(R.drawable.rounded_corner_background)
            )
            .fillMaxSize()
    ) {
        Text(
            text = location,
            style = TextStyle(
                color = ColorProvider(Colors.HavelockBlue),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
            maxLines = 1,
            modifier = GlanceModifier.fillMaxWidth()
        )
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentWeather.temperature2m.toCelcius,
                style = TextStyle(
                    color = ColorProvider(color = Colors.HavelockBlue),
                    fontSize = 24.sp
                ),
                modifier = GlanceModifier.defaultWeight()
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.img_light_rain),
                        contentDescription = null,
                        modifier = GlanceModifier.size(size = 56.dp)
                    )
                    Text(
                        text = currentWeather.weatherCondition.description,
                        style = TextStyle(
                            color = ColorProvider(color = Colors.HavelockBlue),
                            fontSize = 12.sp
                        )
                    )
                }

                Row(
                    modifier = GlanceModifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Feels like",
                        style = TextStyle(
                            color = ColorProvider(color = Colors.Perano),
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = GlanceModifier.width(width = 8.dp))
                    Text(
                        text = currentWeather.apparentTemperature.toCelcius,
                        style = TextStyle(
                            color = ColorProvider(color = Colors.HavelockBlue),
                            fontSize = 16.sp
                        ),
                        modifier = GlanceModifier
                    )
                }
            }
        }
        Spacer(modifier = GlanceModifier.height(height = 16.dp))

        Row(modifier = GlanceModifier.wrapContentWidth()) {
            forecast.hourly.forEach {
                WidgetForecast(
                    iconRes = it.weatherCondition.icon,
                    temperature = it.temperature2m,
                    time = it.timeMillis
                )
            }
        }
    }
}

@Composable
private fun WidgetForecast(
    @DrawableRes iconRes: Int,
    temperature: Double,
    time: Long
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = GlanceModifier.padding(end = 16.dp)
    ) {
        Text(
            text = temperature.toCelcius,
            style = TextStyle(
                color = ColorProvider(Colors.HavelockBlue),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Image(
            provider = ImageProvider(iconRes),
            contentDescription = "",
            modifier = GlanceModifier.size(size = 24.dp)
        )

        Text(
            text = time.toDateString(
                pattern = DatePattern.HOUR_MINUTES_MERIDIEM,
                timeZone = "GMT+8"
            ),
            style = TextStyle(
                color = ColorProvider(color = Colors.HavelockBlue60),
                fontWeight = FontWeight.Normal
            )
        )
    }

}

