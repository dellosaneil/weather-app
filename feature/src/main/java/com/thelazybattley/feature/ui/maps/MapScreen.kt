package com.thelazybattley.feature.ui.maps

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.thelazybattley.feature.R
import com.thelazybattley.feature.util.Colors

private const val SELECTED_ZOOM = 17f
private const val DEFAULT_ZOOM = 11f
private val DEFAULT_LAT_LNG = LatLng(37.3861, 122.0839)

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun MapsScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()

    val searchText = remember { mutableStateOf("") }

    val uiSettings by remember { mutableStateOf(MapUiSettings()) }

    val cameraPositionState = rememberCameraPositionState {
        position =
            CameraPosition.fromLatLngZoom(/* target = */
                viewState.selectedCoordinates?.run {
                    LatLng(latitude, longitude)
                } ?: DEFAULT_LAT_LNG,
                /* zoom = */ DEFAULT_ZOOM
            )
    }
    LaunchedEffect(key1 = viewState.selectedCoordinates) {
        if (viewState.selectedCoordinates != null) {
            val latLng = LatLng(
                viewState.selectedCoordinates!!.latitude,
                viewState.selectedCoordinates!!.longitude
            )
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(
                        latLng, SELECTED_ZOOM, 0f, 0f
                    )
                )
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = MapProperties(mapType = MapType.HYBRID),
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState,
            onMapClick = {
                viewModel.onCoordinatesSelected(coordinates = it)
            }
        ) {
            if (viewState.selectedCoordinates != null) {
                val latLng = LatLng(
                    viewState.selectedCoordinates!!.latitude,
                    viewState.selectedCoordinates!!.longitude
                )
                Marker(
                    state = MarkerState(position = latLng),
                    title = stringResource(R.string.selected_coordinates)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.TopCenter)
        ) {
            TextField(
                value = searchText.value,
                onValueChange = {
                    searchText.value = it
                    viewModel.onSearchBarChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_location),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Colors.Manatee
                        )
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Colors.White,
                    unfocusedContainerColor = Colors.White,
                    focusedTextColor = Colors.Black,
                    unfocusedTextColor = Colors.Black,
                    cursorColor = Colors.MidGray
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable {
                                searchText.value = ""
                                viewModel.onSearchBarChange("")
                            },
                        tint = Colors.Manatee
                    )
                }
            )
            when {
                viewState.isSearchLoading -> {
                    Divider()
                    Box(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .background(color = Colors.White)
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomEnd = 8.dp,
                                    bottomStart = 8.dp
                                )
                            )
                            .fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            color = Colors.RoyalBlue,
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 32.dp)
                                .align(alignment = Alignment.TopCenter)
                        )
                    }
                }

                viewState.searchResults.isNotEmpty() -> {
                    Divider()
                    LazyColumn(
                        modifier = Modifier
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomEnd = 8.dp,
                                    bottomStart = 8.dp
                                )
                            )
                            .background(color = Colors.White)
                            .fillMaxWidth()
                    ) {
                        items(
                            items = viewState.searchResults
                        ) {
                            Text(
                                text = it.getAddressLine(0),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Colors.MidGray
                                ),
                                modifier = Modifier
                                    .basicMarquee()
                                    .clickable {
                                        val coordinates = LatLng(
                                            it.latitude,
                                            it.longitude
                                        )
                                        viewModel.onCoordinatesSelected(coordinates = coordinates)
                                    }
                                    .padding(all = 8.dp)
                                    .fillMaxWidth(),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}
