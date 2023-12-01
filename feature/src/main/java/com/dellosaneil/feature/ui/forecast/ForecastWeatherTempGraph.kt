package com.dellosaneil.feature.ui.forecast

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    minTemps: List<Double>,
    maxTemps: List<Double>,
    dates: List<String>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Canvas(
            modifier = Modifier
                .padding(all = 24.dp)
                .fillMaxWidth()
                .height(250.dp)
        ) {
            val range = maxTemp - minTemp
            val widthPerDate = size.width / minTemps.size

            var previousXOffset = 0f
            var previousLowestTempYOffset = 0f
            var previousHighestTempYOffset = 0f

            minTemps.zip(maxTemps).forEachIndexed { index, (min, max) ->
                val xOffset = widthPerDate * index

                val minTempYOffset = calculateYOffset(
                    maxTemp = maxTemp.toFloat(),
                    temp = min.toFloat(),
                    range = range.toFloat(),
                    height = size.height
                )
                val maxTempYOffset = calculateYOffset(
                    maxTemp = maxTemp.toFloat(),
                    temp = max.toFloat(),
                    range = range.toFloat(),
                    height = size.height
                )
                drawCircle(
                    color = Colors.RoyalBlue,
                    radius = RADIUS,
                    center = Offset(
                        x = xOffset,
                        y = minTempYOffset
                    )
                )

                drawCircle(
                    color = Colors.Crimson,
                    radius = RADIUS,
                    center = Offset(
                        x = xOffset,
                        y = maxTempYOffset
                    )
                )

                val lowestTempStartOffset = calculateMinTempStartOffset(
                    index = index,
                    currentXOffset = xOffset,
                    currentYOffset = minTempYOffset,
                    previousXOffset = previousXOffset,
                    previousYOffset = previousLowestTempYOffset
                )

                val highestTempStartOffset = calculateMinTempStartOffset(
                    index = index,
                    currentXOffset = xOffset,
                    currentYOffset = maxTempYOffset,
                    previousXOffset = previousXOffset,
                    previousYOffset = previousHighestTempYOffset
                )

                drawLine(
                    color = Colors.RoyalBlue,
                    start = lowestTempStartOffset,
                    end = Offset(
                        x = xOffset,
                        y = minTempYOffset
                    ),
                    strokeWidth = STROKE_WIDTH
                )

                drawLine(
                    color = Colors.Crimson,
                    start = highestTempStartOffset,
                    end = Offset(
                        x = xOffset,
                        y = maxTempYOffset
                    ),
                    strokeWidth = STROKE_WIDTH
                )

                previousLowestTempYOffset = minTempYOffset
                previousHighestTempYOffset = maxTempYOffset
                previousXOffset = xOffset
            }
        }
    }
}

private fun calculateMinTempStartOffset(
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
    val maxTemp = 3.0

    val minTemps = listOf(1, 2, 1, 3, 2, 1).map { it.toDouble() }
    val maxTemps = listOf(3, 3, 2, 2, 3, 2).map { it.toDouble() }
    CommonBackground {
        ForecastWeatherTempGraph(
            minTemp = minTemp, maxTemp = maxTemp, minTemps = minTemps, maxTemps = maxTemps,
            dates = listOf("Tue", "Wed", "Thurs", "Fri", "Sat", "Sun")
        )
    }
}
