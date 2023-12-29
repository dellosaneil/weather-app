package com.thelazybattley.feature.ui.today

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thelazybattley.feature.R
import com.thelazybattley.feature.model.hourlyforecast.HourlyForecastData
import com.thelazybattley.feature.model.hourlyforecast.HourlyForecastHourly
import com.thelazybattley.feature.ui.common.CommonBackground
import com.thelazybattley.feature.ui.compositionlocal.LocalWeatherTimeZone
import com.thelazybattley.feature.util.Colors
import com.thelazybattley.feature.util.DatePattern
import com.thelazybattley.feature.util.toCelcius
import com.thelazybattley.feature.util.toDateString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherSummary(
    modifier: Modifier,
    selectedWeather: HourlyForecastHourly,
    columnScope: ColumnScope
) {
    val timeZone = LocalWeatherTimeZone.current.timeZone
    columnScope.apply {
        FilterChip(
            selected = false,
            onClick = {

            },
            label = {
                Text(
                    text = selectedWeather.timeMillis.toDateString(
                        pattern = DatePattern.DATE_MONTH_HOURS_MINUTES_MERIDIEM,
                        timeZone = timeZone
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = Colors.SantasGray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            colors = FilterChipDefaults.filterChipColors(
                containerColor = Colors.Tuna,
            ),
            shape = RoundedCornerShape(size = 16.dp),
            border = null
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Crossfade(targetState = selectedWeather.weatherCondition.icon, label = "") {
                Image(
                    painter = painterResource(id = it),
                    modifier = Modifier
                        .width(width = 200.dp),
                    contentDescription = ""
                )
            }

            Column(
                modifier = Modifier
                    .padding(end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = selectedWeather.temperature2m.toCelcius,
                    style = MaterialTheme.typography.displayLarge.copy(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Colors.SantasGray,
                                Colors.Abbey
                            )
                        ),
                        lineHeight = 0.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = selectedWeather.weatherCondition.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Colors.White,
                        fontSize = 10.sp,
                        lineHeight = 0.sp
                    )
                )
                val feelsLikeAnnotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 10.sp,
                            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                            color = Colors.SantasGray
                        )
                    ) {
                        append(stringResource(id = R.string.feels_like))
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 10.sp,
                            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                            color = Colors.White
                        )
                    ) {
                        append(" ")
                        append(selectedWeather.apparentTemperature.toCelcius)
                    }
                }

                Text(
                    text = feelsLikeAnnotatedString,
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {
    CommonBackground {
        CurrentWeatherSummary(
            modifier = Modifier.padding(all = 16.dp),
            selectedWeather = HourlyForecastData.dummyData().hourly.first(),
            columnScope = it
        )
    }
}
