package com.thelazybattley.feature.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.thelazybattley.feature.util.Colors.DustyGray

@Composable
fun DashedDivider(
    modifier: Modifier = Modifier,
    color: Color = DustyGray
) {
    val pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(10f, 10f), phase = 0f)
    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(1.dp)) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}
