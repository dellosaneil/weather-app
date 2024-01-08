package com.thelazybattley.weather.glance.ui

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
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
import com.thelazybattley.feature.util.toPercentage
import com.thelazybattley.weather.MainActivity
import com.thelazybattley.weather.glance.util.EMPTY_ADDRESS

@Composable
fun CurrentWeatherWidgetScreen(
    forecast: HourlyForecastData,
    location: String,
    context: Context,
    isLoading: Boolean
) {
    Column(
        modifier = GlanceModifier
            .padding(all = 16.dp)
            .background(
                ImageProvider(R.drawable.rounded_corner_background)
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = GlanceModifier.defaultWeight(),
                    color = ColorProvider(color = Colors.HavelockBlue)
                )
            }

            location == EMPTY_ADDRESS -> {
                Text(
                    text = context.getString(R.string.click_here_to_open_application),
                    style = TextStyle(
                        color = ColorProvider(Colors.HavelockBlue),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                )

                Button(
                    text = context.getString(R.string.set_address),
                    style = TextStyle(
                        color = ColorProvider(Colors.White),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    ),
                    onClick = actionStartActivity<MainActivity>(),
                    modifier = GlanceModifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                )
            }

            forecast.hourly.isNotEmpty() -> {
                ShowWidget(
                    forecast = forecast, location = location, context = context
                )
            }

            else -> {
                CircularProgressIndicator(
                    modifier = GlanceModifier.defaultWeight(),
                    color = ColorProvider(color = Colors.HavelockBlue)
                )
            }
        }
    }
}

@Composable
private fun WidgetForecast(
    @DrawableRes iconRes: Int,
    temperature: Double,
    time: Long,
    timeZone: String,
    probability: Int,
    context: Context
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = GlanceModifier
            .padding(horizontal = 8.dp)
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
            provider = ImageProvider(resId = iconRes),
            contentDescription = "",
            modifier = GlanceModifier.size(size = 24.dp)
        )

        Text(
            text = time.toDateString(
                pattern = DatePattern.HOUR_MINUTES_MERIDIEM,
                timeZone = timeZone
            ),
            style = TextStyle(
                color = ColorProvider(color = Colors.HavelockBlue60),
                fontWeight = FontWeight.Normal
            )
        )
        Text(
            text = context.getString(R.string.rain, probability.toPercentage),
            style = TextStyle(
                color = ColorProvider(color = Colors.HavelockBlue60),
                fontWeight = FontWeight.Normal
            )
        )
    }
}


@Composable
private fun ShowWidget(
    forecast: HourlyForecastData,
    location: String,
    context: Context
) {
    val currentWeather = forecast.hourly.first()
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
        modifier = GlanceModifier
            .clickable(actionStartActivity<MainActivity>())
            .fillMaxWidth(),
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
                    provider = ImageProvider(currentWeather.weatherCondition.icon),
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
                    text = context.getString(R.string.feels_like_colon),
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

    Row(
        modifier = GlanceModifier
            .clickable(actionStartActivity<MainActivity>())
            .wrapContentWidth()
    ) {
        forecast.hourly.forEach {
            WidgetForecast(
                iconRes = it.weatherCondition.icon,
                temperature = it.temperature2m,
                time = it.timeMillis,
                timeZone = forecast.timeZone,
                probability = it.precipitationProbability,
                context = context
            )
        }
    }
}
