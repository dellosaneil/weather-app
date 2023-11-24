package com.dellosaneil.feature.currentweather

import androidx.lifecycle.viewModelScope
import com.dellosaneil.domain.network.usecase.GetCurrentWeather
import com.dellosaneil.feature.base.BaseViewModel
import com.dellosaneil.feature.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getCurrentWeather: GetCurrentWeather
): BaseViewModel<CurrentWeatherEvents, CurrentWeatherViewState>() {

    override fun initialState() = CurrentWeatherViewState.initialState()

    init {
        Timber.d("Test: ??")
        viewModelScope.launch(context = dispatcher) {
            getCurrentWeather(city = "canada").also {
                Timber.d("Test: $it")
            }
        }
    }
}

sealed class CurrentWeatherEvents

data class CurrentWeatherViewState(
    val isLoading: Boolean
) {
    companion object {
        fun initialState() = CurrentWeatherViewState(isLoading = true)
    }
}
