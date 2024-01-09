package com.thelazybattley.forecast.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thelazybattley.common.compositionlocal.LocalWeatherTimeZone
import com.thelazybattley.common.model.hourlyforecast.HourlyForecastHourly
import com.thelazybattley.common.ui.CommonBackground
import com.thelazybattley.common.util.Colors
import com.thelazybattley.common.util.DatePattern
import com.thelazybattley.common.util.toCelcius
import com.thelazybattley.common.util.toDateString
import com.thelazybattley.current.model.dailyforecast.DailyForecastDaily
import com.thelazybattley.common.R

@Composable
fun ForecastWeatherDaily(
    modifier: Modifier,
    dailyForecast: List<DailyForecastDaily>
) {
    val timeZone = LocalWeatherTimeZone.current
    val expandedForecast: MutableState<DailyForecastDaily?> =
        remember { mutableStateOf(null) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(size = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        listOf(Colors.DarkGray, Colors.DarkBlueGray)
                    )
                )
                .padding(all = 16.dp),
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

            }

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
                        val rotation by animateFloatAsState(
                            targetValue = if (expandedForecast.value == forecast) 180f else 0f,
                            label = "rotation"
                        )

                        Icon(
                            modifier = Modifier
                                .graphicsLayer {
                                    rotationX = rotation
                                }
                                .padding(end = 16.dp)
                                .clickable {
                                    expandedForecast.value =
                                        if (expandedForecast.value == forecast) {
                                            null
                                        } else {
                                            forecast
                                        }
                                }
                                .padding(all = 4.dp),
                            painter = painterResource(id = R.drawable.ic_expand),
                            contentDescription = "",
                            tint = Colors.White
                        )
                        Text(
                            text = forecast.timeMillis.toDateString(
                                pattern = DatePattern.DATE_MONTH,
                                timeZone = timeZone
                            ),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Colors.White,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Image(
                            painter = painterResource(id = forecast.weatherCondition.icon),
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(20.dp),
                            contentDescription = ""
                        )
                        Text(
                            text = forecast.temperature2mMax.toCelcius,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Colors.White,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = forecast.temperature2mMin.toCelcius,
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
                            forecast.hourlyForecast.forEach {
                                DailyForecastPerHour(forecast = it)
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
private fun DailyForecastPerHour(forecast: HourlyForecastHourly) {
    val timeZone = LocalWeatherTimeZone.current
    FilterChip(
        selected = false,
        onClick = { },
        label = {
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = forecast.weatherCondition.icon),
                    modifier = Modifier.size(size = 36.dp),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier)
                Text(
                    text = forecast.temperature2m.toCelcius,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Colors.White,
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = forecast.timeMillis.toDateString(
                        pattern = DatePattern.HOUR_MINUTES_MERIDIEM,
                        timeZone = timeZone
                    ),
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
    CommonBackground {
        ForecastWeatherDaily(
            modifier = Modifier.padding(all = 16.dp),
            dailyForecast = listOf(
                DailyForecastDaily.dummyData(),
                DailyForecastDaily.dummyData(),
                DailyForecastDaily.dummyData(),
            )
        )
    }
}

