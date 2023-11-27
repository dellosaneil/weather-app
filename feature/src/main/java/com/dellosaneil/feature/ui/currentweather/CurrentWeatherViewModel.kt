package com.dellosaneil.feature.ui.currentweather

import androidx.lifecycle.viewModelScope
import com.dellosaneil.domain.network.usecase.GetCurrentWeather
import com.dellosaneil.feature.base.BaseViewModel
import com.dellosaneil.feature.di.IoDispatcher
import com.dellosaneil.feature.mapper.toData
import com.dellosaneil.feature.model.currentweather.CurrentWeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getCurrentWeather: GetCurrentWeather
) : BaseViewModel<CurrentWeatherEvents, CurrentWeatherViewState>(), CurrentWeatherCallbacks {

    companion object {
        private const val DEBOUNCE_DELAY = 500L
    }

    private var job: Job? = null


    override fun initialState() = CurrentWeatherViewState.initialState()

    override fun fetchCurrentWeather(latitude: String, longitude: String) {
        job?.cancel()
        job = viewModelScope.launch(context = dispatcher) {
            delay(DEBOUNCE_DELAY)
            updateState { state ->
                state.copy(
                    isLoading = true
                )
            }
            getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            ).fold(
                onSuccess = { data ->
                    updateState { state ->
                        state.copy(
                            currentWeatherData = data.toData
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
                    isLoading = false
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}

sealed class CurrentWeatherEvents

data class CurrentWeatherViewState(
    val isLoading: Boolean,
    val currentWeatherData: CurrentWeatherData?,
    val throwable: Throwable?,
) {
    companion object {
        fun initialState() = CurrentWeatherViewState(
            isLoading = true,
            currentWeatherData = null,
            throwable = null
        )
    }
}
