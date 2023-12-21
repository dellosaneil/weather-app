package com.dellosaneil.feature.ui.today

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastData
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastHourly
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.toCelcius
import com.dellosaneil.feature.util.toDateString
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

private const val PLACE_HOLDER_SIZE = 24

@Composable
fun TodayWeatherHourlyForecast(
    modifier: Modifier,
    hourlyForecast: List<HourlyForecastHourly>,
    columnScope: ColumnScope,
    onFilterClicked: (HourlyForecastHourly) -> Unit
) {
    val selectedChip = remember { mutableStateOf(hourlyForecast.first()) }

    columnScope.apply {
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                HourlyWeatherForecast(
                    value = selectedChip.value.precipitationProbability.toString() + "%",
                    drawableRes = R.drawable.img_light_rain,
                    id = "rain",
                    textRes = R.string.precipitation_colon,
                    modifier = Modifier.weight(1f)
                )
                HourlyWeatherForecast(
                    value = selectedChip.value.relativeHumidity2m.toString() + "%",
                    drawableRes = R.drawable.img_humidity,
                    textRes = R.string.humidity_colon,
                    id = "humidity",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HourlyWeatherForecast(
                    value = stringResource(
                        id = R.string.x_km_h,
                        selectedChip.value.windSpeed10m.toString()
                    ),
                    drawableRes = R.drawable.img_wind,
                    id = "wind",
                    textRes = R.string.wind_colon,
                    modifier = Modifier.weight(1f)
                )
                HourlyWeatherForecast(
                    value = selectedChip.value.apparentTemperature.toCelcius,
                    drawableRes = R.drawable.img_temperature,
                    textRes = R.string.feels_like_colon,
                    id = "temp",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        LazyRow(
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
        ) {
            items(
                items = hourlyForecast,
                key = { it.timeMillis.toDateString(pattern = DatePattern.HOURS) }
            ) { forecast ->
                HourlyTimeChips(
                    isSelected = selectedChip.value == forecast,
                    forecast = forecast
                ) {
                    selectedChip.value = forecast
                    onFilterClicked(selectedChip.value)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HourlyTimeChips(
    forecast: HourlyForecastHourly,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    FilterChip(
        selected = isSelected,
        onClick = {
            onClick()
        },
        label = {
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    text = forecast.timeMillis.toDateString(pattern = DatePattern.HOUR_MINUTES_MERIDIEM),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Colors.White
                    )
                )
                GlideImage(
                    imageModel = { forecast.weatherCondition.icon },
                    previewPlaceholder = R.drawable.img_sunny,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = forecast.apparentTemperature.toCelcius,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Colors.White,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Colors.Tuna,
            selectedContainerColor = Colors.White20
        ),
        shape = RoundedCornerShape(size = 16.dp),
        border = FilterChipDefaults.filterChipBorder(
            selectedBorderColor = Colors.White,
            selectedBorderWidth = 1.dp,
            borderColor = Colors.Transparent,
            borderWidth = 0.dp
        )
    )
}


@Composable
private fun HourlyWeatherForecast(
    modifier: Modifier,
    value: String,
    @DrawableRes drawableRes: Int,
    @StringRes textRes: Int,
    id: String
) {
    val text = buildAnnotatedString {
        appendInlineContent(id = id)
        append(" ")
        withStyle(style = SpanStyle(color = Colors.SantasGray)) {
            append(stringResource(id = textRes))
            append(" ")
        }
        withStyle(style = SpanStyle(color = Colors.White)) {
            append(value)
        }
    }

    val inlineContent = mapOf(
        pair = Pair(
            first = id,
            second = InlineTextContent(
                placeholder = Placeholder(
                    width = PLACE_HOLDER_SIZE.sp,
                    height = PLACE_HOLDER_SIZE.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                GlideImage(
                    imageModel = { drawableRes },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Fit
                    ),
                    previewPlaceholder = R.drawable.img_temperature
                )
            }
        )
    )
    Text(
        text = text, inlineContent = inlineContent,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
    )
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {
    CommonBackground {
        TodayWeatherHourlyForecast(
            modifier = Modifier.padding(all = 16.dp),
            hourlyForecast = HourlyForecastData.dummyData().hourly,
            columnScope = it
        ) {

        }
    }
}

