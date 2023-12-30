package com.thelazybattley.feature.ui.history

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thelazybattley.feature.R
import com.thelazybattley.feature.ui.common.CommonBackground
import com.thelazybattley.feature.util.Colors

private const val STEP_COUNT = 10

private const val LEGEND_HEIGHT = 60
private const val CHART_HEIGHT = 250
private const val TOTAL_CHART_HEIGHT = LEGEND_HEIGHT + CHART_HEIGHT
private const val LEGEND_DIVIDER_STROKE_WIDTH = 4f
private const val LEGEND_SYMBOL_STROKE_WIDTH = 6f
private const val LEGEND_SYMBOL_WIDTH = 50f


@Composable
fun HistoryTabScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()
    val events by viewModel.events.collectAsState(initial = null)

    HistoryTabScreen(
        viewState = viewState,
        event = events
    )

}

@Composable
private fun HistoryTabScreen(
    viewState: HistoryState,
    event: HistoryEvents?
) {
    val selectedLegend = remember { mutableStateOf(HistoryLegend.MEAN_TEMPERATURE) }
    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Colors.White
    )

    val legendRects = remember { mutableStateListOf<Rect>() }

    val context = LocalContext.current
    CommonBackground(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(TOTAL_CHART_HEIGHT.dp)
                .border(
                    border = BorderStroke(width = 2.dp, color = Colors.DarkGray),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .pointerInput(legendRects) {
                    detectTapGestures { tapOffset ->
                        for (rect in legendRects) {
                            if (rect.contains(tapOffset)) {
                                val index = legendRects.indexOfFirst {
                                    it.contains(tapOffset)
                                }
                                selectedLegend.value = HistoryLegend.entries[index]
                            }
                        }
                    }
                }
        ) {
            drawChartLegends(
                selectedLegend = selectedLegend,
                textStyle = textStyle,
                textMeasurer = textMeasurer,
                drawScope = this,
                context = context,
                legendRects = legendRects
            )
        }
    }
}

private fun drawChartLegends(
    selectedLegend: MutableState<HistoryLegend>,
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
        val topOffset = CHART_HEIGHT.dp.toPx()
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
            isSelected = selectedLegend.value == HistoryLegend.MEAN_TEMPERATURE
        ) {
            val rect = Rect(
                topLeft = Offset(x = 0f, y = CHART_HEIGHT.dp.toPx()),
                bottomRight = Offset(
                    x = size.width / 2f,
                    y = CHART_HEIGHT.dp.toPx() + (LEGEND_HEIGHT.dp.toPx() / 2f)
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
            isSelected = selectedLegend.value == HistoryLegend.MAX_TEMPERATURE
        ) {
            val rect = Rect(
                topLeft = Offset(
                    x = 0f,
                    y = CHART_HEIGHT.dp.toPx() + (LEGEND_HEIGHT.dp.toPx() / 2f)
                ),
                bottomRight = Offset(
                    x = size.width / 2f,
                    y = CHART_HEIGHT.dp.toPx() + LEGEND_HEIGHT.dp.toPx()
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
            isSelected = selectedLegend.value == HistoryLegend.PRECIPITATION_SUM
        ) {
            val rect = Rect(
                topLeft = Offset(
                    x = size.width / 2f,
                    y = CHART_HEIGHT.dp.toPx() + (LEGEND_HEIGHT.dp.toPx() / 2f)
                ),
                bottomRight = Offset(
                    x = size.width,
                    y = CHART_HEIGHT.dp.toPx() + LEGEND_HEIGHT.dp.toPx()
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
            isSelected = selectedLegend.value == HistoryLegend.MIN_TEMPERATURE
        ) {
            val rect = Rect(
                topLeft = Offset(x = size.width / 2f, y = CHART_HEIGHT.dp.toPx()),
                bottomRight = Offset(
                    x = size.width,
                    y = CHART_HEIGHT.dp.toPx() + (LEGEND_HEIGHT.dp.toPx() / 2f)
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

private enum class HistoryLegend(
    val color: Color,
    @StringRes val textRes: Int,
    val index: Int
) {
    MEAN_TEMPERATURE(color = Colors.Green, textRes = R.string.mean_temperature, index = 0),
    MAX_TEMPERATURE(color = Colors.Crimson, textRes = R.string.max_temperature, index = 1),
    PRECIPITATION_SUM(color = Colors.Yellow, textRes = R.string.precipitation_sum, index = 2),
    MIN_TEMPERATURE(color = Colors.RoyalBlue, textRes = R.string.min_temperature, index = 3),
}


@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun PreviewHistoryTabScreen() {
    HistoryTabScreen(
        viewState = HistoryState(),
        event = null
    )
}
