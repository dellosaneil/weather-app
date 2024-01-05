package com.thelazybattley.feature.ui.history

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thelazybattley.feature.R
import com.thelazybattley.feature.model.history.HistoryData
import com.thelazybattley.feature.util.Colors
import com.thelazybattley.feature.util.DatePattern
import com.thelazybattley.feature.util.roundTwoDecimal
import com.thelazybattley.feature.util.toDateString
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val STEP_COUNT = 12

private const val LEGEND_HEIGHT = 80
private const val CHART_HEIGHT = 250
private const val LABEL_HEIGHT = 60
private const val CHART_LEGEND_PADDING = 20

private const val TOTAL_CHART_HEIGHT =
    LEGEND_HEIGHT + CHART_HEIGHT + LABEL_HEIGHT + CHART_LEGEND_PADDING
private const val LEGEND_DIVIDER_STROKE_WIDTH = 4f
private const val LEGEND_SYMBOL_STROKE_WIDTH = 6f
private const val LEGEND_SYMBOL_WIDTH = 50f

private const val DATA_STROKE_WIDTH = 3f


@Composable
fun HistoryChart(
    modifier: Modifier,
    viewState: HistoryState,
    callbacks: HistoryCallbacks,
    showDateRangePicker: MutableState<Boolean>
) {
    val highlightStartXOffset = remember { mutableFloatStateOf(0f) }
    val highlightWidth = remember { mutableFloatStateOf(0f) }

    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Colors.White
    )

    val coroutineScope = rememberCoroutineScope()

    val legendRects = remember { mutableStateListOf<Rect>() }
    val yAxisWidth = remember { mutableFloatStateOf(0f) }
    val editDateRect = remember { mutableStateOf(Rect(Offset.Zero, Offset.Zero)) }

    val context = LocalContext.current
    val resetAnimation = remember { mutableStateOf(false) }

    val animatedProgress = remember { mutableStateOf(Animatable(0f)) }

    val editDateVector = ImageVector.vectorResource(id = R.drawable.ic_edit)
    val editDatePainter = rememberVectorPainter(image = editDateVector)

    LaunchedEffect(key1 = resetAnimation.value) {
        if (resetAnimation.value) {
            animatedProgress.value.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 0)
            )
            animatedProgress.value.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
            resetAnimation.value = false
        }

    }

    LaunchedEffect(key1 = viewState.selectedLegend) {
        animatedProgress.value.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        resetAnimation.value = false
    }

    LaunchedEffect(key1 = viewState.isLoading) {
        if (!viewState.isLoading) {
            resetAnimation.value = true
        }
    }

    if (viewState.historyData == null) {
        return
    }
    Canvas(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(TOTAL_CHART_HEIGHT.dp)
            .border(
                border = BorderStroke(width = 2.dp, color = Colors.DarkGray),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .pointerInput(key1 = legendRects) {
                detectTapGestures { tapOffset ->
                    for (rect in legendRects) {
                        if (rect.contains(tapOffset)) {
                            val index = legendRects.indexOfFirst {
                                it.contains(tapOffset)
                            }
                            coroutineScope.launch {
                                animatedProgress.value.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(durationMillis = 0)
                                )
                            }
                            callbacks.selectLegend(legend = HistoryLegend.entries[index])
                        }
                    }
                    if (editDateRect.value.contains(tapOffset)) {
                        showDateRangePicker.value = true
                    }
                }
            }
            .pointerInput(key1 = Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = {
                        highlightStartXOffset.floatValue = it.x
                    },
                    onDrag = { _, dragAmount ->
                        val chartWidth = size.width - yAxisWidth.floatValue
                        val rightBorder =
                            highlightWidth.floatValue + highlightStartXOffset.floatValue
                        if (rightBorder in 0f..chartWidth) {
                            highlightWidth.floatValue += dragAmount.x
                        }
                    },
                    onDragCancel = {
                        highlightWidth.floatValue = 0f
                    },
                    onDragEnd = {
                        callbacks.highlightData(
                            xStartOffset = highlightStartXOffset.floatValue,
                            xEndOffset = highlightWidth.floatValue,
                            chartWidth = size.width - yAxisWidth.floatValue
                        )
                        highlightWidth.floatValue = 0f
                    }
                )
            }
            .clipToBounds()
    ) {
        if (viewState.selectedData.isNotEmpty()) {
            drawLabel(
                drawScope = this,
                startDate = viewState.selectedData.first().millis.toDateString(
                    pattern = DatePattern.DATE_MONTH_YEAR,
                    timeZone = viewState.historyData.timezone
                ),
                endDate = viewState.selectedData.last().millis.toDateString(
                    pattern = DatePattern.DATE_MONTH_YEAR,
                    timeZone = viewState.historyData.timezone
                ),
                textMeasurer = textMeasurer,
                textStyle = textStyle,
                editDatePainter = editDatePainter,
                editDateRect = editDateRect
            )
        }

        drawChartLegends(
            selectedLegend = viewState.selectedLegend,
            drawScope = this,
            textStyle = textStyle,
            textMeasurer = textMeasurer,
            context = context,
            legendRects = legendRects
        )
        drawChartYAxisIndicator(
            drawScope = this,
            yAxisWidth = yAxisWidth.floatValue
        )

        drawYAxis(
            drawScope = this,
            textMeasurer = textMeasurer,
            textStyle = textStyle,
            max = viewState.yAxisMaxValue,
            min = viewState.yAxisMinValue,
            label = context.getString(viewState.selectedLegend.labelRes)
        ) {
            yAxisWidth.floatValue = it
        }
        drawChartData(
            drawScope = this,
            yAxisWidth = yAxisWidth.floatValue,
            viewState = viewState,
            animatedState = animatedProgress.value.value
        )
        drawHighlightedArea(
            drawScope = this,
            startXOffset = highlightStartXOffset.floatValue,
            xOffset = highlightWidth.floatValue
        )
    }
}

private fun drawLabel(
    drawScope: DrawScope,
    startDate: String,
    endDate: String,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    editDatePainter: VectorPainter,
    editDateRect: MutableState<Rect>
) {

    val textStartPadding = 50f
    val textEndPadding = 20f
    val textLayoutResult = textMeasurer.measure(
        text = "$startDate - $endDate",
        style = textStyle
    )

    with(drawScope) {
        val yOffset = (LABEL_HEIGHT.dp.toPx() / 2f) - (textLayoutResult.size.height / 2f)
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                x = textStartPadding,
                y = yOffset
            )
        )

        translate(
            top = yOffset,
            left = textStartPadding + textLayoutResult.size.width + textEndPadding
        ) {
            with(editDatePainter) {
                draw(
                    size = Size(
                        width = 24.dp.toPx(),
                        height = 24.dp.toPx()
                    )
                )
            }
            editDateRect.value = Rect(
                topLeft = Offset(
                    x = textStartPadding + textLayoutResult.size.width + textEndPadding,
                    y = yOffset
                ),
                bottomRight = Offset(
                    x = textStartPadding + textLayoutResult.size.width + textEndPadding + 24.dp.toPx(),
                    y = yOffset + 24.dp.toPx()
                )
            )
        }
    }
}


private fun drawHighlightedArea(
    drawScope: DrawScope, startXOffset: Float, xOffset: Float
) {
    with(drawScope) {
        drawRect(
            color = Colors.Tuna,
            alpha = 0.9f,
            topLeft = Offset(
                x = startXOffset,
                y = LEGEND_HEIGHT.dp.toPx() - CHART_LEGEND_PADDING.dp.toPx()
            ),
            size = Size(
                width = xOffset,
                height = CHART_HEIGHT.dp.toPx()
            )
        )
    }
}

private fun drawChartData(
    drawScope: DrawScope,
    yAxisWidth: Float,
    viewState: HistoryState,
    animatedState: Float
) {
    val range = viewState.yAxisMaxValue - viewState.yAxisMinValue
    with(drawScope) {
        val widthPerPoint =
            (size.width - yAxisWidth) / viewState.selectedData.size.dec()
        val chartHeightWithLabel = (CHART_HEIGHT.dp.toPx() + LABEL_HEIGHT.dp.toPx())

        val points = viewState.selectedData
            .map { it.data }
            .mapIndexed { index, point ->
                Offset(
                    x = index * widthPerPoint,
                    y = chartHeightWithLabel - ((point - viewState.yAxisMinValue) / range * CHART_HEIGHT.dp.toPx()).toFloat()
                )
            }

        val animatedPoints = points
            .take(n = (animatedState * points.size).roundToInt())

        drawPoints(
            points = animatedPoints,
            color = viewState.selectedLegend.color,
            pointMode = PointMode.Polygon,
            strokeWidth = DATA_STROKE_WIDTH
        )
    }
}

private fun drawYAxis(
    drawScope: DrawScope,
    textStyle: TextStyle,
    textMeasurer: TextMeasurer,
    max: Double,
    min: Double,
    label: String,
    yAxisWidth: (Float) -> Unit
) {
    val step = (max - min) / STEP_COUNT
    val measuredTexts = run {
        val list = mutableListOf<TextLayoutResult>()
        repeat(STEP_COUNT.inc()) { index ->
            textMeasurer.measure(
                text = "${(max - (step * index)).roundTwoDecimal}",
                style = textStyle
            ).also {
                list.add(it)
            }
        }
        list.toList()
    }
    val yAxisLabel = textMeasurer.measure(
        text = label,
        style = textStyle
    )

    val width = measuredTexts.maxOf { it.size.width.toFloat() } + yAxisLabel.size.height
    yAxisWidth(width)
    with(drawScope) {
        measuredTexts.forEachIndexed { index, text ->
            drawText(
                textLayoutResult = text,
                topLeft = Offset(
                    x = size.width - width,
                    y = LABEL_HEIGHT.dp.toPx() + (CHART_HEIGHT.dp.toPx() * (index.toFloat() / STEP_COUNT)) - text.size.height / 2
                )
            )
        }
        rotate(
            degrees = 90f,
            pivot = Offset(
                x = size.width,
                y = (LABEL_HEIGHT.dp.toPx() + (CHART_HEIGHT.dp.toPx()) / 2f)
            )
        ) {
            drawText(
                textLayoutResult = yAxisLabel,
                topLeft = Offset(
                    x = size.width - (yAxisLabel.size.width / 2f),
                    y = (LABEL_HEIGHT.dp.toPx() + (CHART_HEIGHT.dp.toPx()) / 2f)
                )
            )
        }
    }
}

private fun drawChartYAxisIndicator(drawScope: DrawScope, yAxisWidth: Float) {
    with(drawScope) {
        repeat(STEP_COUNT.inc()) { index ->
            val yOffset =
                CHART_HEIGHT.dp.toPx() * (index.toFloat() / STEP_COUNT) + LABEL_HEIGHT.dp.toPx()
            drawLine(
                color = Colors.Abbey,
                start = Offset(
                    x = 0f,
                    y = yOffset
                ),
                end = Offset(
                    x = size.width - yAxisWidth,
                    y = yOffset
                )
            )
        }
    }
}

private fun drawChartLegends(
    selectedLegend: HistoryLegend,
    drawScope: DrawScope,
    textStyle: TextStyle,
    textMeasurer: TextMeasurer,
    context: Context,
    legendRects: SnapshotStateList<Rect>
) {
    legendRects.clear()
    val meanTemp = textMeasurer.measure(
        text = context.getString(HistoryLegend.MEAN_TEMPERATURE.textRes),
        style = textStyle
    )
    val maxTemp = textMeasurer.measure(
        text = context.getString(HistoryLegend.MAX_TEMPERATURE.textRes),
        style = textStyle
    )

    val precipitationSum = textMeasurer.measure(
        text = context.getString(HistoryLegend.PRECIPITATION_SUM.textRes),
        style = textStyle
    )
    val minTemp = textMeasurer.measure(
        text = context.getString(HistoryLegend.MIN_TEMPERATURE.textRes),
        style = textStyle
    )

    with(drawScope) {
        val topOffset =
            CHART_HEIGHT.dp.toPx() + LABEL_HEIGHT.dp.toPx() + CHART_LEGEND_PADDING.dp.toPx()
        val legendHeight = LEGEND_HEIGHT.dp.toPx()
        val firstRowYOffset = topOffset + (legendHeight / 4f)
        val secondRowYOffset = topOffset + legendHeight - legendHeight / 4f

        drawLine(
            color = Colors.DarkGray,
            start = Offset(x = 0f, y = topOffset),
            end = Offset(x = size.width, y = topOffset),
            strokeWidth = LEGEND_DIVIDER_STROKE_WIDTH
        )

        drawLegend(
            drawScope = this,
            textLayoutResult = meanTemp,
            startOffsetSymbol = Offset(
                x = 50f,
                y = firstRowYOffset
            ),
            endOffsetSymbol = Offset(
                x = LEGEND_SYMBOL_WIDTH + 50f,
                y = firstRowYOffset
            ),
            offsetText = Offset(
                x = 120f,
                y = firstRowYOffset - (meanTemp.size.height / 2f)
            ),
            legend = HistoryLegend.MEAN_TEMPERATURE,
            isSelected = selectedLegend == HistoryLegend.MEAN_TEMPERATURE
        ) {
            val rect = Rect(
                topLeft = Offset(
                    x = 0f,
                    y = CHART_HEIGHT.dp.toPx() + LABEL_HEIGHT.dp.toPx() + CHART_LEGEND_PADDING.dp.toPx()
                ),
                bottomRight = Offset(
                    x = size.width / 2f,
                    y = CHART_HEIGHT.dp.toPx() + (LEGEND_HEIGHT.dp.toPx() / 2f) + LABEL_HEIGHT.dp.toPx() + CHART_LEGEND_PADDING.dp.toPx()
                )
            )
            legendRects.add(rect)
        }

        drawLegend(
            drawScope = this,
            textLayoutResult = maxTemp,
            startOffsetSymbol = Offset(
                x = 50f,
                y = secondRowYOffset
            ),
            endOffsetSymbol = Offset(
                x = LEGEND_SYMBOL_WIDTH + 50f,
                y = secondRowYOffset
            ),
            offsetText = Offset(
                x = 120f,
                y = secondRowYOffset - (maxTemp.size.height / 2f)
            ),
            legend = HistoryLegend.MAX_TEMPERATURE,
            isSelected = selectedLegend == HistoryLegend.MAX_TEMPERATURE
        ) {
            val rect = Rect(
                topLeft = Offset(
                    x = 0f,
                    y = CHART_HEIGHT.dp.toPx() + (LEGEND_HEIGHT.dp.toPx() / 2f) + LABEL_HEIGHT.dp.toPx() + CHART_LEGEND_PADDING.dp.toPx()
                ),
                bottomRight = Offset(
                    x = size.width / 2f,
                    y = CHART_HEIGHT.dp.toPx() + LEGEND_HEIGHT.dp.toPx() + LABEL_HEIGHT.dp.toPx() + CHART_LEGEND_PADDING.dp.toPx()
                )
            )
            legendRects.add(rect)
        }

        drawLegend(
            drawScope = this,
            textLayoutResult = precipitationSum,
            startOffsetSymbol = Offset(
                x = 50f + size.width / 2f,
                y = secondRowYOffset
            ),
            endOffsetSymbol = Offset(
                x = LEGEND_SYMBOL_WIDTH + 50f + size.width / 2f,
                y = secondRowYOffset
            ),
            offsetText = Offset(
                x = 120f + size.width / 2f,
                y = secondRowYOffset - (maxTemp.size.height / 2f)
            ),
            legend = HistoryLegend.PRECIPITATION_SUM,
            isSelected = selectedLegend == HistoryLegend.PRECIPITATION_SUM
        ) {
            val rect = Rect(
                topLeft = Offset(
                    x = size.width / 2f,
                    y = CHART_HEIGHT.dp.toPx() + (LEGEND_HEIGHT.dp.toPx() / 2f) + LABEL_HEIGHT.dp.toPx() + CHART_LEGEND_PADDING.dp.toPx()
                ),
                bottomRight = Offset(
                    x = size.width,
                    y = CHART_HEIGHT.dp.toPx() + LEGEND_HEIGHT.dp.toPx() + LABEL_HEIGHT.dp.toPx() + CHART_LEGEND_PADDING.dp.toPx()
                )
            )
            legendRects.add(rect)
        }

        drawLegend(
            drawScope = this,
            textLayoutResult = minTemp,
            startOffsetSymbol = Offset(
                x = 50f + size.width / 2f,
                y = firstRowYOffset
            ),
            endOffsetSymbol = Offset(
                x = LEGEND_SYMBOL_WIDTH + 50f + size.width / 2f,
                y = firstRowYOffset
            ),
            offsetText = Offset(
                x = 120f + size.width / 2f,
                y = firstRowYOffset - (minTemp.size.height / 2f)
            ),
            legend = HistoryLegend.MIN_TEMPERATURE,
            isSelected = selectedLegend == HistoryLegend.MIN_TEMPERATURE
        ) {
            val rect = Rect(
                topLeft = Offset(
                    x = size.width / 2f,
                    y = CHART_HEIGHT.dp.toPx() + LABEL_HEIGHT.dp.toPx() + CHART_LEGEND_PADDING.dp.toPx()
                ),
                bottomRight = Offset(
                    x = size.width,
                    y = CHART_HEIGHT.dp.toPx() + (LEGEND_HEIGHT.dp.toPx() / 2f) + LABEL_HEIGHT.dp.toPx() + CHART_LEGEND_PADDING.dp.toPx()
                )
            )
            legendRects.add(rect)
        }
    }
}

private fun drawLegend(
    drawScope: DrawScope,
    textLayoutResult: TextLayoutResult,
    startOffsetSymbol: Offset,
    endOffsetSymbol: Offset,
    offsetText: Offset,
    legend: HistoryLegend,
    isSelected: Boolean,
    setRect: () -> Unit
) {
    val alpha = if (isSelected) 1f else 0.4f
    with(drawScope) {
        drawLine(
            color = legend.color,
            start = startOffsetSymbol,
            end = endOffsetSymbol,
            strokeWidth = LEGEND_SYMBOL_STROKE_WIDTH,
            alpha = alpha
        )

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = offsetText,
            alpha = alpha
        )
        setRect()
    }
}

enum class HistoryLegend(
    val color: Color,
    @StringRes val textRes: Int,
    @StringRes val labelRes: Int
) {
    MEAN_TEMPERATURE(
        color = Colors.Green, textRes = R.string.mean_temperature,
        labelRes = R.string.celcius
    ),
    MAX_TEMPERATURE(
        color = Colors.Crimson, textRes = R.string.max_temperature,
        labelRes = R.string.celcius
    ),
    PRECIPITATION_SUM(
        color = Colors.Yellow, textRes = R.string.precipitation_sum,
        labelRes = R.string.millimeter
    ),
    MIN_TEMPERATURE(
        color = Colors.RoyalBlue, textRes = R.string.min_temperature,
        labelRes = R.string.celcius
    ),
}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun PreviewHistoryTabScreen() {
    HistoryChart(
        modifier = Modifier,
        viewState = HistoryState(
            historyData = HistoryData.createDummy()
        ),
        showDateRangePicker = mutableStateOf(false),
        callbacks = object : HistoryCallbacks {
            override fun selectLegend(legend: HistoryLegend) {
                TODO("Not yet implemented")
            }

            override fun highlightData(xStartOffset: Float, xEndOffset: Float, chartWidth: Float) {
                TODO("Not yet implemented")
            }

            override fun selectDateRange(startMillis: Long, endMillis: Long) {
                TODO("Not yet implemented")
            }
        }
    )
}

