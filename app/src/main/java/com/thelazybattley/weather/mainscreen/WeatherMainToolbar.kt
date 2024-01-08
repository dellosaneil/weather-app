package com.thelazybattley.weather.mainscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thelazybattley.common.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WeatherMainToolbar(
    location: String,
    onSearch: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
            ) {
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = com.thelazybattley.common.util.Colors.White,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1,
                    modifier = Modifier
                        .basicMarquee()
                        .weight(1f)
                )
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
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = com.thelazybattley.common.util.Colors.MidGray
        )
    )
}


@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {
    WeatherMainToolbar(location = "Davao City, Philippines, Davao City, Philippines ,Davao City, Philippines") {

    }
}
