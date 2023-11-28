package com.dellosaneil.feature.ui.today

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.model.dailyforecast.DailyForecast
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.toCelcius
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TodayWeatherDailyForecast(
    dailyForecast: List<DailyForecast>
) {
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
            dailyForecast.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        imageModel = { R.drawable.ic_expand },
                        previewPlaceholder = R.drawable.ic_expand,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        text = it.day,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Colors.White,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    GlideImage(
                        imageModel = { it.icon.iconRes },
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(20.dp),
                        previewPlaceholder = R.drawable.img_rain
                    )
                    Text(
                        text = it.highestTempC.toCelcius,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Colors.White,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = it.lowestTempC.toCelcius,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Colors.White,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
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
                DailyForecast.dummyData(),
                DailyForecast.dummyData(),
            )
        )
    }
}
