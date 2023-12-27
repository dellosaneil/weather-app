package com.thelazybattley.feature.ui.forecast

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.thelazybattley.feature.R
import com.thelazybattley.feature.model.hourlyforecast.HourlyForecastHourly
import com.thelazybattley.feature.ui.common.CommonBackground
import com.thelazybattley.feature.util.Colors
import com.thelazybattley.feature.util.DatePattern
import com.thelazybattley.feature.util.roundTwoDecimal
import com.thelazybattley.feature.util.toCelcius
import com.thelazybattley.feature.util.toDateString

private const val RADIUS = 8f
private const val STROKE_WIDTH = 6f
private const val Y_AXIS_STEP_COUNT = 12
private const val WIDTH_PADDING = 200f
private const val HEIGHT_PADDING = 50f


@Composable
fun ForecastWeatherTempGraph(
    forecast: List<HourlyForecastHourly>,
    showMore: MutableState<Boolean>,
    highestTemp: Double,
    lowestTemp: Double
) {
    val showMoreOffset = remember { mutableStateOf(Offset.Zero) }
    val showMoreDetails: MutableState<HourlyForecastHourly?> = remember {
        mutableStateOf(null)
    }

    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val tempStep =
        (highestTemp - lowestTemp) / Y_AXIS_STEP_COUNT
    val graphLabelOffset = density.run {
        Offset(
            y = -36.dp.toPx(),
            x = 0f
        )
    }
    val graphTypography = MaterialTheme.typography.bodyMedium
    val labelText = stringResource(R.string.hourly_temperature_projection_chart)
    val pointRects = remember {
        mutableStateListOf<Rect>()
    }
    val distancePerPoint = remember { mutableFloatStateOf(0f) }
    Box {
        Canvas(
            modifier = Modifier
                .padding(
                    all = 16.dp
                )
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = Colors.DarkGray,
                    ),
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp,
                    top = 56.dp
                )
                .fillMaxWidth()
                .height(250.dp)
                .pointerInput(key1 = forecast) {
                    detectTapGestures { tapOffset ->
                        var isFound = false
                        for (rect in pointRects) {
                            if (rect.contains(tapOffset)) {
                                val index = pointRects.indexOfFirst {
                                    it.contains(tapOffset)
                                }
                                showMoreDetails.value = forecast[index]
                                showMoreOffset.value = tapOffset
                                isFound = true
                                break
                            }
                        }
                        showMore.value = isFound
                    }
                }
        ) {
            val range = highestTemp - lowestTemp
            val graphSize = Size(
                width = size.width - WIDTH_PADDING,
                height = size.height - HEIGHT_PADDING
            )
            distancePerPoint.floatValue = graphSize.width / forecast.size
            drawText(
                text = labelText,
                textMeasurer = textMeasurer,
                topLeft = graphLabelOffset,
                style = graphTypography.copy(
                    color = Colors.White
                )
            )
            drawYAxis(
                drawScope = this,
                textMeasurer = textMeasurer,
                minTemp = lowestTemp,
                tempStep = tempStep,
                graphSize = graphSize,
                textStyle = graphTypography
            )

            plotPoints(
                size = graphSize,
                temperatures = forecast.map { it.temperature2m },
                maxTemp = highestTemp.toFloat(),
                range = range.toFloat(),
                drawScope = this,
                widthPerTimeStamp = distancePerPoint.floatValue,
                rect = pointRects
            )
        }
        if (showMore.value) {
            val size = remember { mutableStateOf(IntSize.Zero) }
            ShowMoreDetails(
                modifier = Modifier
                    .onGloballyPositioned {
                        size.value = it.size
                    }
                    .offset {
                        IntOffset(
                            x = showMoreOffset.value.x.toInt(),
                            y = showMoreOffset.value.y.toInt() - size.value.height / 2
                        )
                    },
                details = showMoreDetails.value!!
            )
        }
    }
}

@Composable
private fun ShowMoreDetails(
    modifier: Modifier,
    details: HourlyForecastHourly,
) {
    Column(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(size = 8.dp)
            )
            .background(color = Colors.Tuna)
            .border(
                border = BorderStroke(width = 1.dp, color = Colors.White),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(
                all = 8.dp
            ),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = details.timeMillis.toDateString(pattern = DatePattern.HOUR_MINUTES_MERIDIEM),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Colors.White
            )
        )
        Image(
            painter = painterResource(id = details.weatherCondition.icon),
            modifier = Modifier.size(
                size = 36.dp
            ),
            contentDescription = ""
        )
        Text(
            text = details.temperature2m.toCelcius,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Colors.White
            )
        )
    }
}

private fun plotPoints(
    size: Size,
    temperatures: List<Double>,
    maxTemp: Float,
    range: Float,
    drawScope: DrawScope,
    widthPerTimeStamp: Float,
    rect: MutableList<Rect>
) {
    rect.clear()
    var previousOffset = Offset.Zero
    temperatures.forEachIndexed { index, temp ->

        val xOffset = (widthPerTimeStamp * index) + 25f

        val yOffset = calculateTempYOffset(
            maxTemp = maxTemp,
            temp = temp.toFloat(),
            range = range,
            height = size.height
        )

        val startOffset = calculateTempStartOffset(
            index = index,
            currentXOffset = xOffset,
            currentYOffset = yOffset,
            previousXOffset = previousOffset.x,
            previousYOffset = previousOffset.y
        )
        val centerOffset = Offset(
            x = xOffset,
            y = yOffset
        )
        with(drawScope) {
            drawCircle(
                color = Colors.RoyalBlue,
                radius = RADIUS,
                center = centerOffset
            )
            rect.add(
                Rect(
                    center = centerOffset,
                    radius = RADIUS + 25f
                ),
            )

            drawLine(
                color = Colors.RoyalBlue,
                start = startOffset,
                end = centerOffset,
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
    minTemp: Double,
    tempStep: Double,
    graphSize: Size,
    textStyle: TextStyle
) {
    with(drawScope) {
        repeat(Y_AXIS_STEP_COUNT + 1) { index ->
            val yAxisLabel = minTemp + (index * tempStep)
            val yAxisOffset = calculateYAxisOffset(
                graphWidth = graphSize.width,
                graphHeight = graphSize.height,
                index = index + 1
            )

            drawText(
                text = yAxisLabel.roundTwoDecimal.toCelcius,
                textMeasurer = textMeasurer,
                topLeft = yAxisOffset,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                style = textStyle.copy(
                    color = Colors.StormGray
                )
            )
            drawLine(
                color = Colors.Abbey,
                start = Offset(
                    x = 0f,
                    y = yAxisOffset.y + 25f
                ),
                end = Offset(
                    y = yAxisOffset.y + 25f,
                    x = graphSize.width
                ),
                strokeWidth = 3f
            )
        }
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

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Preview() {
    CommonBackground {
        ForecastWeatherTempGraph(
            forecast = listOf(
                HourlyForecastHourly.dummyData().copy(
                    temperature2m = 32.2,
                ),
                HourlyForecastHourly.dummyData().copy(
                    temperature2m = 31.2
                ),
                HourlyForecastHourly.dummyData().copy(
                    temperature2m = 26.4
                ),
                HourlyForecastHourly.dummyData().copy(
                    temperature2m = 35.6
                ),
            ),
            showMore = mutableStateOf(false),
            lowestTemp = 26.4,
            highestTemp = 35.6
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun PreviewShowMore() {
    Box(modifier = Modifier.padding(all = 16.dp)) {
        ShowMoreDetails(
            details = HourlyForecastHourly.dummyData(),
            modifier = Modifier
        )
    }
}
