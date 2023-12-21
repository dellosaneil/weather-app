package com.dellosaneil.feature.ui.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.model.dailyforecast.DailyForecastDaily
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.toCelcius
import com.dellosaneil.feature.util.toDateString
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastWeatherDailySummary(
    modifier: Modifier,
    dailyForecast: List<DailyForecastDaily>,
    selectedDailyForecast: DailyForecastDaily,
    onClick: (DailyForecastDaily) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(all = 8.dp)
    ) {
        items(
            items = dailyForecast, key = { it.timeMillis }
        ) { forecast ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 8.dp),
                selected = selectedDailyForecast.timeMillis == forecast.timeMillis,
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
                ),
                onClick = {
                    onClick(forecast)
                }, label = {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GlideImage(
                            imageModel = {
                                forecast.weatherCondition.icon
                            },
                            modifier = Modifier.size(size = 24.dp),
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Fit
                            )
                        )
                        Text(
                            text = forecast.timeMillis.toDateString(pattern = DatePattern.DAY_DATE_MONTH),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Colors.Manatee
                            )
                        )
                        Spacer(modifier = Modifier)
                        Text(
                            text = forecast.temperature2mMin.toCelcius,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Colors.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {
    CommonBackground {
        ForecastWeatherDailySummary(
            modifier = Modifier,
            dailyForecast = listOf(
                DailyForecastDaily.dummyData()
            ),
            selectedDailyForecast = DailyForecastDaily.dummyData()
        ) {

        }
    }
}
