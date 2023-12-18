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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.roundTwoDecimal
import com.dellosaneil.feature.util.toCelcius

private const val RADIUS = 12f
private const val STROKE_WIDTH = 6f
private const val Y_AXIS_STEP_COUNT = 12
private const val WIDTH_PADDING = 150f
private const val HEIGHT_PADDING = 50f


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
        val textMeasurer = rememberTextMeasurer()
        val tempStep = (maxTemp - minTemp) / Y_AXIS_STEP_COUNT


        Canvas(
            modifier = Modifier
                .padding(all = 24.dp)
                .fillMaxWidth()
                .height(250.dp)
        ) {
            val range = maxTemp - minTemp
            val graphWidth = size.width - WIDTH_PADDING
            val graphHeight = size.height - HEIGHT_PADDING

            plotPoints(
                size = Size(
                    width = graphWidth,
                    height = graphHeight,
                ),
                drawScope = this,
                temperatures = hourlyTemp,
                maxTemp = maxTemp.toFloat(),
                range = range.toFloat()
            )

            drawYAxis(
                drawScope = this,
                textMeasurer = textMeasurer,
                graphHeight = graphHeight,
                graphWidth = graphWidth,
                minTemp = minTemp,
                tempStep = tempStep,
                maxTemp = maxTemp
            )
        }
    }
}

private fun plotPoints(
    size: Size,
    temperatures: List<Double>,
    maxTemp: Float,
    range: Float,
    drawScope: DrawScope,
) {
    var previousOffset = Offset.Zero
    val widthPerTimeStamp = size.width / temperatures.size
    temperatures.forEachIndexed { index, temp ->

        val xOffset = (widthPerTimeStamp * index)

        val yOffset = calculateTempYOffset(
            maxTemp = maxTemp,
            temp = temp.toFloat(),
            range = range,
            height = size.height
        )

        val tempStartOffset = calculateTempStartOffset(
            index = index,
            currentXOffset = xOffset,
            currentYOffset = yOffset,
            previousXOffset = previousOffset.x,
            previousYOffset = previousOffset.y
        )
        with(drawScope) {
            drawCircle(
                color = Colors.RoyalBlue,
                radius = RADIUS,
                center = Offset(
                    x = xOffset,
                    y = yOffset
                )
            )

            drawLine(
                color = Colors.RoyalBlue,
                start = tempStartOffset,
                end = Offset(
                    x = xOffset,
                    y = yOffset
                ),
                strokeWidth = STROKE_WIDTH
            )
        }
        previousOffset = Offset(
            x = xOffset,
            y = yOffset
        )
    }
}

private fun drawYAxis(
    drawScope: DrawScope,
    textMeasurer: TextMeasurer,
    graphHeight: Float,
    graphWidth: Float,
    minTemp: Double,
    tempStep: Double,
    maxTemp: Double
) {
    with(drawScope) {
        repeat(Y_AXIS_STEP_COUNT) { index ->
            val yAxisLabel = minTemp + (index * tempStep)
            val yAxisOffset = calculateYAxisOffset(
                graphWidth = graphWidth,
                graphHeight = graphHeight,
                index = index + 1
            )

            drawText(
                text = yAxisLabel.roundTwoDecimal.toCelcius,
                textMeasurer = textMeasurer,
                topLeft = yAxisOffset,
                maxLines = 1,
                overflow = TextOverflow.Visible
            )
            drawLine(
                color = Colors.Abbey,
                start = Offset(
                    x = 0f,
                    y = yAxisOffset.y + 25f
                ),
                end = Offset(
                    y = yAxisOffset.y + 25f,
                    x = graphWidth
                ),
                strokeWidth = 3f
            )
        }

        drawText(
            text = maxTemp.roundTwoDecimal.toCelcius,
            textMeasurer = textMeasurer,
            topLeft = Offset(
                x = graphWidth + 25f,
                y = -25f
            ),
            maxLines = 1,
            overflow = TextOverflow.Visible
        )
        drawLine(
            color = Colors.Abbey,
            start = Offset(
                x = 0f,
                y = 0f
            ),
            end = Offset(
                y = 0f,
                x = graphWidth
            ),
            strokeWidth = 3f
        )
    }
}

private fun calculateYAxisOffset(
    graphWidth: Float,
    graphHeight: Float,
    index: Int
): Offset {
    return Offset(
        x = graphWidth + 25f,
        y = (graphHeight - (index.toFloat() / Y_AXIS_STEP_COUNT) * graphHeight) + 25f
    )
}

private fun calculateTempStartOffset(
    index: Int,
    currentXOffset: Float,
    currentYOffset: Float,
    previousXOffset: Float,
    previousYOffset: Float
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

private fun calculateTempYOffset(
    maxTemp: Float,
    temp: Float,
    range: Float,
    height: Float
): Float {
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
            hourlyTimeStamp = listOf(
                "6:00 am",
                "7:00 am",
                "8:00 am",
                "9:00 am",
                "10:00am",
                "11:00am"
            )
        )
    }
}
