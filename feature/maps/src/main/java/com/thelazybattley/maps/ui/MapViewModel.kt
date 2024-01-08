package com.thelazybattley.maps.ui

import android.location.Address
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.thelazybattley.common.base.BaseViewModel
import com.thelazybattley.common.di.IoDispatcher
import com.thelazybattley.domain.local.model.userlocation.UserLocation
import com.thelazybattley.domain.local.usecase.GetLocationUseCase
import com.thelazybattley.domain.local.usecase.InsertLocationUseCase
import com.thelazybattley.domain.usecase.GetAddress
import com.thelazybattley.domain.usecase.SearchAddressList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val searchAddressList: SearchAddressList,
    private val getAddress: GetAddress,
    private val insertLocationUseCase: InsertLocationUseCase,
    private val getLocationUseCase: GetLocationUseCase
) : BaseViewModel<MapEvents, MapState>(), MapCallbacks {

    companion object {
        private const val MAX_RESULTS = 5
    }

    override fun initialState() = MapState.initialState()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(context = dispatcher) {
            delay(250L)
            getLocationUseCase().collect { userLocation ->
                userLocation?.let {
                    onCoordinatesSelected(
                        coordinates = LatLng(
                            it.latitude,
                            it.longitude
                        )
                    )
                }
            }
        }
    }


    override fun onSearchBarChange(name: String) {
        searchJob?.cancel()
        if (name.isEmpty()) {
            updateState { state ->
                state.copy(
                    isSearchLoading = false,
                    searchResults = emptyList()
                )
            }
            return
        }
        searchJob = viewModelScope.launch(context = dispatcher) {
            updateState { state ->
                state.copy(
                    isSearchLoading = true
                )
            }
            delay(500)
            searchLocation(name)
        }
    }

    private suspend fun searchLocation(name: String) {
        searchAddressList(
            name = name, MAX_RESULTS
        ).fold(
            onSuccess = {
                updateState { state ->
                    state.copy(
                        searchResults = it,
                        throwable = null
                    )
                }
            },
            onFailure = {
                updateState { state ->
                    state.copy(
                        throwable = it
                    )
                }
            }
        )
        updateState { state ->
            state.copy(
                isSearchLoading = false
            )
        }
    }

    override fun onCoordinatesSelected(coordinates: LatLng) {
        viewModelScope.launch(context = dispatcher) {
            updateState { state ->
                state.copy(
                    throwable = null,
                    selectedCoordinates = coordinates
                )
            }

            getAddress(coordinates = coordinates).fold(
                onSuccess = {
                    updateState { state ->
                        state.copy(
                            throwable = null,
                            selectedAddress = it
                        )
                    }
                },
                onFailure = {
                    updateState { state ->
                        state.copy(
                            throwable = it
                        )
                    }
                }
            )
        }
    }

    override fun onSaveAddress() {
        viewModelScope.launch(context = dispatcher) {
            val currentState = getCurrentState()
            if (currentState.selectedAddress == null && currentState.selectedCoordinates != null) {
                return@launch
            }
            insertLocationUseCase(
                userLocation = UserLocation(
                    latitude = currentState.selectedCoordinates!!.latitude,
                    longitude = currentState.selectedCoordinates.longitude,
                    address = currentState.selectedAddress!!.getAddressLine(0)
                )
            )
            emitEvent(event = MapEvents.OnLocationSaved)
        }
    }
}

sealed interface MapEvents {

    data object OnLocationSaved : MapEvents

}

data class MapState(
    val selectedCoordinates: LatLng?,
    val searchResults: List<Address>,
    val throwable: Throwable?,
    val selectedAddress: Address?,
    val isSearchLoading: Boolean
) {
    companion object {
        fun initialState() = MapState(
            selectedCoordinates = null,
            searchResults = emptyList(),
            throwable = null,
            selectedAddress = null,
            isSearchLoading = false
        )
    }
}
