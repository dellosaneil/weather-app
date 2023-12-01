package com.dellosaneil.feature.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.util.Colors

@Composable
fun CommonBackground(
    modifier: Modifier = Modifier,
    screen: @Composable (ColumnScope) -> Unit
) {
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Colors.Trout, Colors.Shark)
                    )
                )
                .padding(bottom = 16.dp)
                .fillMaxSize()
        ) {
            screen(this)
        }
    }
}
