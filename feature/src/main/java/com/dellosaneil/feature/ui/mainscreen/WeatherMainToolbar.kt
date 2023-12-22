package com.dellosaneil.feature.ui.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dellosaneil.feature.R
import com.dellosaneil.feature.util.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherMainToolbar(
    location: String,
    onSearch: () -> Unit
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.MidGray
        ),
        actions = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                modifier = Modifier
                    .padding(all = 8.dp)
                    .clip(shape = CircleShape)
                    .size(size = 24.dp)
                    .clickable {
                        onSearch()
                    },
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    )
}


@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {
    WeatherMainToolbar(location = "Davao City, Philippines") {

    }
}
