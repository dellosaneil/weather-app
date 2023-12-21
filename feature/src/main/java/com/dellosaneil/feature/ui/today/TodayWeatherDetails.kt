package com.dellosaneil.feature.ui.today

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastData
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastHourly
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.metersToKm
import com.dellosaneil.feature.util.toCelcius
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TodayWeatherDetails(
    modifier: Modifier,
    sunrise: String,
    sunset: String,
    selectedHour: HourlyForecastHourly
) {
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
                .padding(vertical = 16.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.details),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Colors.White
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Crossfade(
                    modifier = Modifier.weight(1f),
                    targetState = selectedHour.weatherCondition.icon,
                    label = ""
                ) {
                    GlideImage(
                        imageModel = { it },
                        previewPlaceholder = R.drawable.img_light_rain,
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Fit
                        )
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(space = 16.dp)
                ) {
                    Details(
                        label = stringResource(id = R.string.feels_like),
                        value = selectedHour.temperature2m.toCelcius
                    )

                    Details(
                        label = stringResource(id = R.string.humidity),
                        value = "${selectedHour.relativeHumidity2m}%"
                    )
                    Details(
                        label = stringResource(R.string.visibility),
                        value = stringResource(
                            id = R.string.x_km,
                            selectedHour.visibility.metersToKm
                        )
                    )
                    Details(
                        label = stringResource(R.string.pressure),
                        value = stringResource(id = R.string.x_mb, selectedHour.surfacePressure.toInt())
                    )
                    Details(
                        label = stringResource(R.string.sunrise),
                        value = sunrise
                    )
                    Details(
                        label = stringResource(R.string.sunset),
                        value = sunset
                    )
                }
            }
        }
    }
}

@Composable
private fun Details(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Colors.White50
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Colors.White
            )
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {

    CommonBackground {
        TodayWeatherDetails(
            modifier = Modifier.padding(all = 16.dp),
            selectedHour = HourlyForecastData.dummyData().hourly.first(),
            sunset = "10:00am",
            sunrise = "33:00am"
        )
    }
}
