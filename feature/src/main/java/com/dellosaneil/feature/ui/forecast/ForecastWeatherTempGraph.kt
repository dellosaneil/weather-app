package com.dellosaneil.feature.ui.forecast

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors

private const val RADIUS = 12f
private const val STROKE_WIDTH = 6f

@Composable
fun ForecastWeatherTempGraph(
    minTemp: Double,
    maxTemp: Double,
    hourlyTemp: List<Double>,
    hourlyTimeStamp: List<String>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = Colors.DarkGray,
                ),
                shape = RoundedCornerShape(size = 16.dp)
            )
    ) {
        Canvas(
            modifier = Modifier
                .padding(all = 24.dp)
                .fillMaxWidth()
                .height(250.dp)
        ) {
            val range = maxTemp - minTemp
            val widthPerDate = size.width / hourlyTemp.size

            var previousXOffset = 0f
            var previousTempYOffset = 0f

            hourlyTemp.forEachIndexed { index, temp ->
                val xOffset = widthPerDate * index

                val tempYOffset = calculateYOffset(
                    maxTemp = maxTemp.toFloat(),
                    temp = temp.toFloat(),
                    range = range.toFloat(),
                    height = size.height
                )

                drawLine(
                    color = Colors.Abbey,
                    start = Offset(
                        x = xOffset,
                        y = tempYOffset
                    ),
                    end = Offset(
                        y = tempYOffset,
                        x = size.width
                    ),
                    strokeWidth = STROKE_WIDTH
                )

                drawCircle(
                    color = Colors.RoyalBlue,
                    radius = RADIUS,
                    center = Offset(
                        x = xOffset,
                        y = tempYOffset
                    )
                )

                val tempStartOffset = calculateTempStartOffset(
                    index = index,
                    currentXOffset = xOffset,
                    currentYOffset = tempYOffset,
                    previousXOffset = previousXOffset,
                    previousYOffset = previousTempYOffset
                )

                drawLine(
                    color = Colors.RoyalBlue,
                    start = tempStartOffset,
                    end = Offset(
                        x = xOffset,
                        y = tempYOffset
                    ),
                    strokeWidth = STROKE_WIDTH
                )

                previousTempYOffset = tempYOffset
                previousXOffset = xOffset
            }
        }
    }
}

private fun calculateTempStartOffset(
    index: Int, currentXOffset: Float, currentYOffset: Float,
    previousXOffset: Float, previousYOffset: Float
): Offset {
    return if (index == 0) {
        Offset(
            x = currentXOffset,
            y = currentYOffset
        )
    } else {
        Offset(
            x = previousXOffset,
            y = previousYOffset
        )
    }
}

private fun calculateYOffset(maxTemp: Float, temp: Float, range: Float, height: Float): Float {
    return ((maxTemp - temp) * height / range)
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Preview() {
    val minTemp = 1.0
    val maxTemp = 7.0

    val hourlyTemp = listOf(1, 3, 4, 6, 2, 7).map { it.toDouble() }
    CommonBackground {
        ForecastWeatherTempGraph(
            minTemp = minTemp, maxTemp = maxTemp, hourlyTemp = hourlyTemp,
            hourlyTimeStamp = listOf("6:00 am", "7:00 am", "8:00 am", "9:00 am")
        )
    }
}
