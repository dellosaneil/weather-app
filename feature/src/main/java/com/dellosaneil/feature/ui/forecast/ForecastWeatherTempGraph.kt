package com.dellosaneil.feature.ui.forecast

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.toCelcius

private const val CORNER_RADIUS = 24f
private const val HORIZONTAL_SPACE = 36f
private const val END_PADDING = 150f


@Composable
fun ForecastWeatherTempGraph(
    minTemp: Double,
    maxTemp: Double,
    minTemps: List<Double>,
    maxTemps: List<Double>,
    dates: List<String>
) {
    val textMeasurer = rememberTextMeasurer()
    val style = MaterialTheme.typography.bodyMedium.copy(
        color = Colors.White,
        fontWeight = FontWeight.Medium
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {

//            drawLine(
//                color = Colors.White,
//                start = Offset(x = 0f, y = 0f),
//                end = Offset(x = size.width, y = 0f)
//            )
//
//            drawLine(
//                color = Colors.White,
//                start = Offset(x = 0f, y = size.height),
//                end = Offset(x = size.width, y = size.height)
//            )

            val barWidth =
                (size.width / minTemps.size) - HORIZONTAL_SPACE - (HORIZONTAL_SPACE / minTemps.size) - (END_PADDING / minTemps.size)

            repeat(minTemps.size) { index ->

                val xOffset = (index * barWidth) + (index * HORIZONTAL_SPACE) + HORIZONTAL_SPACE
                val yOffset = size.height - ((maxTemps[index] / maxTemp) * size.height)
                val height =
                    (size.height / (maxTemp - minTemp + 1)) * (maxTemps[index] - minTemps[index] + 1)

                val measuredText =
                    textMeasurer.measure(
                        text = dates[index],
                        style = style
                    )

                drawRoundRect(
                    color = Colors.SantasGray,
                    size = size.copy(
                        width = barWidth,
                        height = height.toFloat()
                    ),
                    cornerRadius = CornerRadius(x = CORNER_RADIUS, y = CORNER_RADIUS),
                    topLeft = Offset(x = xOffset, y = yOffset.toFloat())
                )
                drawText(
                    textLayoutResult = measuredText,
                    topLeft = Offset(x = xOffset, y = size.height)
                )
            }


            val count = maxTemp - minTemp + 2
            repeat(count.toInt()) { index ->
                val measuredText =
                    textMeasurer.measure(
                        text = (maxTemp + 1 - index).toCelcius,
                        style = style
                    )
                val (yOffsetText, yOffsetLine) =
                    when (index) {
                        0 -> style.fontSize.toPx() / 4 to 0f
                        else -> {
                            index.toFloat() / count.toFloat() * size.height + style.fontSize.toPx() / 4 to index.toFloat() / count.toFloat() * size.height
                        }
                    }

                val xOffset = size.width - END_PADDING

                drawLine(
                    color = Colors.White,
                    start = Offset(x = 0f, y = yOffsetLine),
                    end = Offset(x = xOffset, y = yOffsetLine)
                )

                drawText(
                    textLayoutResult = measuredText,
                    topLeft = Offset(x = xOffset, y = yOffsetText - style.fontSize.toPx())
                )
            }
            val measuredText =
                textMeasurer.measure(
                    text = (minTemp - 1).toCelcius,
                    style = style
                )
            drawText(
                textLayoutResult = measuredText,
                topLeft = Offset(x = size.width - END_PADDING, y = size.height - style.fontSize.toPx())
            )

            drawLine(
                color = Colors.White,
                start = Offset(x = 0f, y = size.height),
                end = Offset(x = size.width - END_PADDING, y = size.height)
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Preview() {
    val minTemp = 1.0
    val maxTemp = 3.0

    val minTemps = listOf(1, 2, 2, 2, 3, 1).map { it.toDouble() }
    val maxTemps = listOf(3, 3, 2, 2, 3, 1).map { it.toDouble() }
    CommonBackground {
        ForecastWeatherTempGraph(
            minTemp = minTemp, maxTemp = maxTemp, minTemps = minTemps, maxTemps = maxTemps,
            dates = listOf("Tue", "Wed", "Thurs", "Fri", "Sat", "Sun")
        )
    }
}
