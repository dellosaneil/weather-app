package com.dellosaneil.feature.ui.forecast

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.roundTwoDecimal
import com.dellosaneil.feature.util.toPercentage

private const val Y_AXIS_STEP_COUNT = 11
private const val Y_AXIS_INDICATOR_STROKE_WIDTH = 3f
private const val QUANTITY_BAR_WIDTH = 150f
private const val CIRCLE_RADIUS = 8f

@Composable
fun ForecastWeatherPrecipitationGraph() {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.bodyMedium
    val localDensity = LocalDensity.current

    val quantityAxisSize = remember { mutableStateOf(DpSize.Zero) }
    val probabilityAxisSize = remember { mutableStateOf(DpSize.Zero) }

    val graphLabel = stringResource(R.string.hourly_precipitation_graph)
    val graphLabelOffset = localDensity.run {
        Offset(
            y = -36.dp.toPx(),
            x = 0f
        )
    }

    val xOffset = remember { mutableFloatStateOf(0f) }


    Row(
        modifier = Modifier
            .padding(
                all = 16.dp
            )
            .fillMaxWidth()
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
                .padding(
                    start = 16.dp,
                    bottom = 24.dp,
                    top = 56.dp
                )
                .height(250.dp)
                .width(probabilityAxisSize.value.width)
        ) {
            drawPrecipitationProbabilityYAxis(
                scope = this,
                textMeasurer = textMeasurer,
                textStyle = textStyle,
                probabilityAxisSize = probabilityAxisSize
            )
        }
        Canvas(
            modifier = Modifier
                .padding(
                    bottom = 24.dp,
                    top = 56.dp
                )
                .weight(1f)
                .height(250.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        xOffset.floatValue += dragAmount
                    }
                }
                .clipToBounds(),
        ) {
            drawText(
                text = graphLabel,
                textMeasurer = textMeasurer,
                topLeft = graphLabelOffset,
                style = textStyle.copy(
                    color = Colors.White
                )
            )
            drawYAxisIndicator(
                scope = this
            )
            translate(left = xOffset.floatValue, top = 0f) {
                plotProbabilityPoints(
                    scope = this
                )
            }
        }

        Canvas(
            modifier = Modifier
                .padding(
                    bottom = 24.dp,
                    top = 56.dp,
                    start = 4.dp,
                    end = 16.dp
                )
                .height(250.dp)
                .width(width = quantityAxisSize.value.width)
        ) {
            drawPrecipitationQuantityYAxis(
                scope = this,
                textMeasurer = textMeasurer,
                textStyle = textStyle,
                quantityAxisSize = quantityAxisSize
            )
        }
    }
}

private fun plotProbabilityPoints(
    scope: DrawScope
) {
    val percentages = listOf(
        0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100
    )
    with(scope) {
        percentages.forEachIndexed { index, percentage ->
            drawCircle(
                color = Colors.RoyalBlue,
                radius = CIRCLE_RADIUS,
                center = Offset(
                    x = (QUANTITY_BAR_WIDTH * index),
                    y = size.height - (size.height / 100f * percentage)
                )
            )
        }
    }
}

private fun drawPrecipitationQuantityYAxis(
    scope: DrawScope,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    quantityAxisSize: MutableState<DpSize>
) {
    val lowestMeasurement = 0.0
    val highestMeasurement = 9.0
    val step = (highestMeasurement - lowestMeasurement) / Y_AXIS_STEP_COUNT.dec()

    val measurements =
        (0 until Y_AXIS_STEP_COUNT).map { lowestMeasurement + it * step }.asReversed()

    with(scope) {
        measurements.forEachIndexed { index, measurement ->
            val measuredText = textMeasurer.measure(
                text = "${measurement.roundTwoDecimal} mm",
                style = textStyle.copy(
                    color = Colors.StormGray
                )
            )
            drawText(
                textLayoutResult = measuredText,
                topLeft = Offset(
                    y = (size.height * index / 10f) - measuredText.size.height / 2f,
                    x = 0f
                )
            )
            if (quantityAxisSize.value.width.toPx() < measuredText.size.width) {
                quantityAxisSize.value = DpSize(
                    width = measuredText.size.width.toDp(),
                    height = measuredText.size.height.toDp()
                )
            }
        }
    }
}

private fun drawYAxisIndicator(
    scope: DrawScope,
) {
    with(scope) {
        repeat(Y_AXIS_STEP_COUNT) {
            val yOffset = size.height * it / 10f
            drawLine(
                color = Colors.Abbey,
                start = Offset(
                    x = 0f,
                    y = yOffset
                ),
                end = Offset(
                    x = size.width,
                    y = yOffset
                ),
                strokeWidth = Y_AXIS_INDICATOR_STROKE_WIDTH
            )
        }
    }
}

private fun drawPrecipitationProbabilityYAxis(
    scope: DrawScope,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    probabilityAxisSize: MutableState<DpSize>,
) {
    val percentages = 100 downTo 0 step 10
    with(scope) {
        percentages.forEachIndexed { index, percentage ->
            val measuredText = textMeasurer.measure(
                text = percentage.toPercentage,
                style = textStyle.copy(
                    color = Colors.StormGray
                )
            )
            drawText(
                textLayoutResult = measuredText,
                topLeft = Offset(
                    y = (size.height * index / 10f) - measuredText.size.height / 2f,
                    x = 0f
                ),
            )
            if (probabilityAxisSize.value.width.toPx() < measuredText.size.width) {
                probabilityAxisSize.value = DpSize(
                    width = measuredText.size.width.toDp(),
                    height = measuredText.size.height.toDp()
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun ForecastWeatherPrecipitationGraphPreview() {
    CommonBackground {
        ForecastWeatherPrecipitationGraph()
    }
}
