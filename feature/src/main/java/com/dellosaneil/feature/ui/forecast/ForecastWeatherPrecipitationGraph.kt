package com.dellosaneil.feature.ui.forecast

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.model.dailyforecast.DailyForecastDaily
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastHourly
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.roundTwoDecimal
import com.dellosaneil.feature.util.toDateString
import com.dellosaneil.feature.util.toPercentage

private const val Y_AXIS_STEP_COUNT = 11
private const val Y_AXIS_INDICATOR_STROKE_WIDTH = 3f
private const val QUANTITY_BAR_WIDTH = 250f
private const val CIRCLE_RADIUS = 8f
private const val PROBABILITY_STROKE_WIDTH = 6f
private const val GRAPH_HEIGHT = 250

@Composable
fun ForecastWeatherPrecipitationGraph(
    forecast: DailyForecastDaily
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.bodyMedium

    val rightYAxisSize = remember { mutableStateOf(DpSize.Zero) }
    val leftYAxisSize = remember { mutableStateOf(DpSize.Zero) }

    val xOffset = remember(key1 = forecast) { mutableFloatStateOf(0f) }
    val offsetEdge = remember { mutableFloatStateOf(0f) }


    val scale = remember { mutableFloatStateOf(1f) }

    val barWidth = remember { mutableFloatStateOf(QUANTITY_BAR_WIDTH) }

    val transformableState = rememberTransformableState { zoom, _, _ ->
        scale.floatValue = (scale.floatValue * zoom).coerceIn(1f, 5f)
        barWidth.floatValue = QUANTITY_BAR_WIDTH / scale.floatValue
    }

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
            ).transformable(state = transformableState)
    ) {
        YAxisPercentageCanvas(
            width = leftYAxisSize.value.width,
            textMeasurer = textMeasurer,
            textStyle = textStyle,
            axisSize = leftYAxisSize
        )
        PrecipitationPointsCanvas(
            rowScope = this,
            offsetEdge = offsetEdge,
            xOffset = xOffset,
            forecast = forecast,
            textMeasurer = textMeasurer,
            density = density,
            textStyle = textStyle,
            scale = scale,
            barWidth = barWidth
        )

        YAxisQuantityCanvas(
            axisSize = rightYAxisSize,
            textStyle = textStyle,
            textMeasurer = textMeasurer,
            minQuantity = forecast.minPrecipitationQuantity,
            maxQuantity = forecast.maxPrecipitationQuantity
        )
    }
}

@Composable
private fun PrecipitationPointsCanvas(
    rowScope: RowScope,
    offsetEdge: MutableFloatState,
    xOffset: MutableFloatState,
    forecast: DailyForecastDaily,
    textMeasurer: TextMeasurer,
    density: Density,
    textStyle: TextStyle,
    scale: MutableFloatState,
    barWidth: MutableFloatState
) {
    val labelOffset = density.run {
        Offset(
            y = 20.dp.toPx(),
            x = 0f
        )
    }
    with(rowScope) {
        val measuredText = textMeasurer.measure(
            text = stringResource(id = R.string.hourly_precipitation_graph),
            style = textStyle.copy(
                color = Colors.White
            ),
        )

        Canvas(
            modifier = Modifier
                .clipToBounds()
                .drawBehind {
                    drawText(
                        textLayoutResult = measuredText,
                        topLeft = labelOffset
                    )
                    translate(left = xOffset.floatValue, top = 0f) {
                        xAxisLabels(
                            scope = this@drawBehind,
                            forecasts = forecast.hourlyForecast,
                            textMeasurer = textMeasurer,
                            textStyle = textStyle,
                            barWidth = barWidth.floatValue
                        )
                    }
                }
                .padding(
                    bottom = 36.dp,
                    top = 56.dp
                )
                .weight(1f)
                .height(GRAPH_HEIGHT.dp)
                .pointerInput(key1 = offsetEdge.floatValue) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        val newOffset = xOffset.floatValue + dragAmount
                        val totalOffset = (newOffset * -1) + size.width
                        if (newOffset < 0 && (offsetEdge.floatValue + barWidth.floatValue / 2f) > totalOffset) {
                            xOffset.floatValue += dragAmount
                        }
                    }
                }
                .clipToBounds(),
        ) {
            drawYAxisIndicator(
                scope = this
            )
            translate(left = xOffset.floatValue, top = 0f) {
                plotQuantityBar(
                    scope = this,
                    quantity = forecast.hourlyForecast.map { it.precipitation },
                    highestQuantity = forecast.maxPrecipitationQuantity,
                    lowestQuantity = forecast.minPrecipitationQuantity,
                    barWidth = barWidth.floatValue
                )

                plotProbabilityPoints(
                    scope = this,
                    percentages = forecast.hourlyForecast.map { it.precipitationProbability },
                    barWidth = barWidth.floatValue
                ) {
                    offsetEdge.floatValue = it
                }
            }
        }
    }
}

fun xAxisLabels(
    scope: DrawScope,
    forecasts: List<HourlyForecastHourly>,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle, barWidth: Float
) {
    with(scope) {
        forecasts.forEachIndexed { index, forecast ->
            val measuredText = textMeasurer.measure(
                text = forecast.timeMillis.toDateString(pattern = DatePattern.HOUR_MINUTES_MERIDIEM),
                style = textStyle.copy(
                    color = Colors.White
                )
            )
            drawText(
                textLayoutResult = measuredText,
                topLeft = Offset(
                    y = size.height - measuredText.size.height,
                    x = (barWidth / 2) + (index * barWidth) - measuredText.size.width / 2
                )
            )
        }
    }
}

@Composable
private fun YAxisQuantityCanvas(
    axisSize: MutableState<DpSize>,
    textStyle: TextStyle,
    textMeasurer: TextMeasurer,
    minQuantity: Double,
    maxQuantity: Double
) {
    Canvas(
        modifier = Modifier
            .padding(
                bottom = 24.dp,
                top = 56.dp,
                start = 4.dp,
                end = 16.dp
            )
            .height(GRAPH_HEIGHT.dp)
            .width(width = axisSize.value.width)
    ) {
        drawPrecipitationQuantityYAxis(
            scope = this,
            textMeasurer = textMeasurer,
            textStyle = textStyle,
            quantityAxisSize = axisSize,
            lowestQuantity = minQuantity,
            highestQuantity = maxQuantity
        )
    }
}

@Composable
private fun YAxisPercentageCanvas(
    width: Dp,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    axisSize: MutableState<DpSize>,
) {
    Canvas(
        modifier = Modifier
            .padding(
                start = 16.dp,
                bottom = 24.dp,
                top = 56.dp
            )
            .height(GRAPH_HEIGHT.dp)
            .width(width = width)
    ) {
        drawPrecipitationProbabilityYAxis(
            scope = this,
            textMeasurer = textMeasurer,
            textStyle = textStyle,
            probabilityAxisSize = axisSize
        )
    }
}

private fun plotQuantityBar(
    scope: DrawScope,
    quantity: List<Double>,
    lowestQuantity: Double,
    highestQuantity: Double, barWidth: Float
) {
    with(scope) {
        quantity.forEachIndexed { index, item ->
            val height = (size.height * (item / (highestQuantity - lowestQuantity))).toFloat()
            drawRect(
                color = Colors.StormGray,
                size = Size(
                    width = barWidth,
                    height = height
                ),
                topLeft = Offset(
                    x = (barWidth * index),
                    y = size.height - height
                )
            )
        }
    }
}

private fun plotProbabilityPoints(
    scope: DrawScope,
    percentages: List<Int>, barWidth: Float,
    lastPointPosition: (Float) -> Unit
) {
    var previousOffset = Offset.Zero
    with(scope) {
        percentages.forEachIndexed { index, percentage ->
            val xOffset = (barWidth * index) + (barWidth / 2f)
            val yOffset = size.height - (size.height / 100f * percentage)
            val currentOffset = Offset(
                x = xOffset,
                y = yOffset
            )
            drawCircle(
                color = Colors.RoyalBlue,
                radius = CIRCLE_RADIUS,
                center = currentOffset
            )
            if (index == percentages.size - 1) {
                lastPointPosition(xOffset)
            }
            if (index > 0) {
                drawLine(
                    color = Colors.RoyalBlue,
                    start = previousOffset,
                    end = currentOffset,
                    strokeWidth = PROBABILITY_STROKE_WIDTH
                )
            }
            previousOffset = Offset(
                x = xOffset,
                y = yOffset
            )
        }
    }
}

private fun drawPrecipitationQuantityYAxis(
    scope: DrawScope,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    lowestQuantity: Double,
    highestQuantity: Double,
    quantityAxisSize: MutableState<DpSize>
) {
    val step = (highestQuantity - lowestQuantity) / Y_AXIS_STEP_COUNT.dec()

    val measurements =
        (0 until Y_AXIS_STEP_COUNT).map { lowestQuantity + it * step }.asReversed()

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
                    color = Colors.RoyalBlue
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
        ForecastWeatherPrecipitationGraph(
            forecast = DailyForecastDaily.dummyData().copy(
                hourlyForecast = DailyForecastDaily.dummyData().hourlyForecast + DailyForecastDaily.dummyData().hourlyForecast
            )
        )
    }
}
