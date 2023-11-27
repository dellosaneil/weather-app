package com.dellosaneil.feature.ui.today

import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.model.currentweather.CurrentWeatherData
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.toCelcius
import com.dellosaneil.feature.util.toDateString
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherSummary(
    currentWeather: CurrentWeatherData,
    columnScope: ColumnScope
) {
    columnScope.apply {
        FilterChip(
            selected = false,
            onClick = {

            },
            label = {
                val currentLocalDate =
                    currentWeather.currentTimeMillis.toDateString(
                        pattern = DatePattern.DAY_DATE_MONTH
                    )
                Text(
                    text = currentLocalDate,
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
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            GlideImage(
                imageModel = { currentWeather.weather.first().weatherIconEnum.iconRes },
                previewPlaceholder = R.drawable.img_sunny,
                modifier = Modifier
                    .width(width = 200.dp),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit
                )
            )

            Column(
                modifier = Modifier
                    .padding(end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = currentWeather.main.tempC.toCelcius,
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
                    text = currentWeather.weather.first().description,
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
                        append(currentWeather.main.feelsLikeC.toCelcius)
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Colors.Trout, Colors.Shark)
                )
            )
    ) {
        CurrentWeatherSummary(
            currentWeather = CurrentWeatherData.dummyData(),
            columnScope = this
        )
    }
}
