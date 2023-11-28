package com.dellosaneil.feature.ui.today

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.model.dailyforecast.DailyForecast
import com.dellosaneil.feature.model.dailyforecast.DailyForecastHourly
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.toCelcius
import com.dellosaneil.feature.util.toDateString
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TodayWeatherDailyForecast(
    dailyForecast: List<DailyForecast>
) {
    val expandedForecast: MutableState<DailyForecast?> =
        remember { mutableStateOf(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(size = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Colors.Shark,
                            Colors.Tuna
                        )
                    )
                )
                .padding(all = 16.dp),
        ) {
            dailyForecast.forEach { forecast ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val drawableRes = if (expandedForecast.value == forecast) {
                            R.drawable.ic_collapse
                        } else {
                            R.drawable.ic_expand
                        }
                        GlideImage(
                            imageModel = { drawableRes },
                            previewPlaceholder = R.drawable.ic_expand,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable {
                                    expandedForecast.value =
                                        if (expandedForecast.value == forecast) {
                                            null
                                        } else {
                                            forecast
                                        }
                                }
                                .padding(all = 4.dp)
                        )
                        Text(
                            text = forecast.day,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Colors.White,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        GlideImage(
                            imageModel = { forecast.icon.iconRes },
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(20.dp),
                            previewPlaceholder = R.drawable.img_rain
                        )
                        Text(
                            text = forecast.highestTempC.toCelcius,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Colors.White,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = forecast.lowestTempC.toCelcius,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Colors.White,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    AnimatedVisibility(visible = expandedForecast.value == forecast) {
                        Row(
                            modifier = Modifier.horizontalScroll(
                                state = rememberScrollState()
                            ),
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                        ) {
                            forecast.hourly.forEach { dailyForecast ->
                                DailyForecastPerHour(forecast = dailyForecast)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DailyForecastPerHour(forecast: DailyForecastHourly) {
    FilterChip(
        selected = false,
        onClick = { },
        label = {
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                GlideImage(
                    imageModel = { forecast.icon.iconRes },
                    modifier = Modifier.size(size = 36.dp),
                    previewPlaceholder = R.drawable.img_wind
                )
                Spacer(modifier = Modifier)
                Text(
                    text = forecast.tempC.toCelcius,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Colors.White,
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = forecast.dateTimeMillis.toDateString(pattern = DatePattern.HOUR_MINUTES_MERIDIEM),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Colors.White,
                        fontSize = 12.sp
                    )
                )
            }
        },
        border = null,
        shape = RoundedCornerShape(size = 16.dp),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Colors.Tuna,
        )
    )
}


@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Colors.Trout, Colors.Shark)
                )
            )
    ) {
        TodayWeatherDailyForecast(
            dailyForecast = listOf(
                DailyForecast.dummyData(),
                DailyForecast.dummyData().copy(highestTempC = 2.3),
                DailyForecast.dummyData().copy(highestTempC = 32.1),
            )
        )
    }
}
