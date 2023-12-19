package com.dellosaneil.feature.ui.forecast

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.model.dailyforecast.DailyForecast
import com.dellosaneil.feature.model.dailyforecast.DailyForecastHourly
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.WeatherIconEnum
import com.dellosaneil.feature.util.roundTwoDecimal
import com.dellosaneil.feature.util.toCelcius
import com.dellosaneil.feature.util.toDateString
import com.skydoves.landscapist.glide.GlideImage

private const val RADIUS = 12f
private const val STROKE_WIDTH = 6f
private const val Y_AXIS_STEP_COUNT = 12
private const val WIDTH_PADDING = 200f
private const val HEIGHT_PADDING = 50f


@Composable
fun ForecastWeatherTempGraph(
    forecast: DailyForecast,
    showMore: MutableState<Boolean>
) {
    val showMoreOffset = remember { mutableStateOf(Offset.Zero) }
    val showMoreDetails: MutableState<DailyForecastHourly?> = remember {
        mutableStateOf(null)
    }

    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val tempStep = (forecast.highestTempC - forecast.lowestTempC) / Y_AXIS_STEP_COUNT
    val graphLabelOffset = density.run {
        Offset(
            y = -36.dp.toPx(),
            x = 0f
        )
    }
    val graphTypography = MaterialTheme.typography.bodyMedium
    val labelText = stringResource(R.string._3_hourly_temperature_projection_chart)
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
                                showMoreDetails.value = forecast.hourly[index]
                                showMoreOffset.value = tapOffset
                                isFound = true
                                break
                            }
                        }
                        showMore.value = isFound
                    }
                }
        ) {
            val range = forecast.highestTempC - forecast.lowestTempC
            val graphSize = Size(
                width = size.width - WIDTH_PADDING,
                height = size.height - HEIGHT_PADDING
            )
            distancePerPoint.floatValue = graphSize.width / forecast.temperatures.size
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
                minTemp = forecast.lowestTempC,
                tempStep = tempStep,
                graphSize = graphSize,
                textStyle = graphTypography
            )

            plotPoints(
                size = graphSize,
                temperatures = forecast.temperatures,
                maxTemp = forecast.highestTempC.toFloat(),
                range = range.toFloat(),
                drawScope = this,
                widthPerTimeStamp = distancePerPoint.floatValue,
                rect = pointRects
            )

            forecast.timeStamp.forEachIndexed { index, timeStamp ->
                val xOffset = (distancePerPoint.floatValue * index)
                drawText(
                    text = timeStamp,
                    textMeasurer = textMeasurer,
                    topLeft = Offset(
                        x = xOffset,
                        y = graphSize.height
                    ),
                    style = graphTypography.copy(
                        color = Colors.StormGray
                    )
                )
            }
        }
        if (showMore.value) {
            ShowMoreDetails(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = showMoreOffset.value.x.toInt() + (distancePerPoint.floatValue / 2f).toInt(),
                            y = showMoreOffset.value.y.toInt() - 150
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
    details: DailyForecastHourly,
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
            text = details.dateTimeMillis.toDateString(pattern = DatePattern.HOUR_MINUTES_MERIDIEM),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Colors.White
            )
        )
        GlideImage(
            imageModel = {
                details.icon.iconRes
            },
            previewPlaceholder = R.drawable.img_light_rain,
            modifier = Modifier.size(
                size = 36.dp
            ),
        )
        Text(
            text = details.tempC.toCelcius,
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

        val xOffset = (widthPerTimeStamp * index) + 50f

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
            forecast = DailyForecast.dummyData(),
            showMore = mutableStateOf(false)
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun PreviewShowMore() {
    Box(modifier = Modifier.padding(all = 16.dp)) {
        ShowMoreDetails(
            details = DailyForecastHourly(
                tempC = 33.0,
                icon = WeatherIconEnum.MIST_SUN,
                dateTimeMillis = 1701075282549L
            ),
            modifier = Modifier
        )
    }
}
