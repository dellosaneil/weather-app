package com.thelazybattley.weather.mainscreen

import androidx.lifecycle.viewModelScope
import com.thelazybattley.common.base.BaseViewModel
import com.thelazybattley.domain.local.usecase.GetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherMainViewModel @Inject constructor(
    @com.thelazybattley.common.di.IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getLocationUseCase: GetLocationUseCase
) : BaseViewModel<WeatherMainEvent, WeatherMainState>() {

    override fun initialState() = WeatherMainState.initialState()

    init {
        viewModelScope.launch(context = dispatcher) {
            getLocationUseCase().collect {
                updateState { state ->
                    state.copy(
                        address = it?.address ?: "No Address"
                    )
                }
            }
        }
    }
}

sealed interface WeatherMainEvent {

}

data class WeatherMainState(
    val address: String
) {
    companion object {
        fun initialState() = WeatherMainState(address = "No Address")
    }
}
