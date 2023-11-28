package com.dellosaneil.feature.ui.mainscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.util.Colors
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherMainToolbar(
    location: String
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = location,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Colors.White,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        navigationIcon = {
            GlideImage(
                imageModel = {
                    R.drawable.ic_menu
                },
                previewPlaceholder = R.drawable.ic_menu,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.MidGray
        )
    )
}


@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {
    WeatherMainToolbar(location = "Davao City, Philippines")
}
