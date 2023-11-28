package com.dellosaneil.feature.ui.today

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.model.currentweather.CurrentWeatherData
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.metersToKm
import com.dellosaneil.feature.util.toCelcius
import com.dellosaneil.feature.util.toDateString
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TodayWeatherDetails(
    modifier: Modifier,
    currentWeatherData: CurrentWeatherData
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
                GlideImage(
                    imageModel = {
                        currentWeatherData.weather.first().weatherIconEnum.iconRes
                    },
                    modifier = Modifier
                        .weight(1f),
                    previewPlaceholder = R.drawable.img_sunny
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(space = 16.dp)
                ) {
                    Details(
                        label = stringResource(id = R.string.feels_like),
                        value = currentWeatherData.main.feelsLikeC.toCelcius
                    )

                    Details(
                        label = stringResource(id = R.string.humidity),
                        value = "${currentWeatherData.main.humidity}%"
                    )
                    Details(
                        label = stringResource(R.string.visibility),
                        value = stringResource(
                            id = R.string.x_km,
                            currentWeatherData.visibility.metersToKm
                        )
                    )
                    Details(
                        label = stringResource(R.string.pressure),
                        value = stringResource(id = R.string.x_mb, currentWeatherData.main.pressure)
                    )
                    Details(
                        label = stringResource(R.string.sunrise),
                        value = currentWeatherData.sys.sunriseMillis.toDateString(pattern = DatePattern.HOUR_MINUTES_MERIDIEM)
                    )
                    Details(
                        label = stringResource(R.string.sunset),
                        value = currentWeatherData.sys.sunsetMillis.toDateString(pattern = DatePattern.HOUR_MINUTES_MERIDIEM)
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
            currentWeatherData = CurrentWeatherData.dummyData()
        )
    }
}
