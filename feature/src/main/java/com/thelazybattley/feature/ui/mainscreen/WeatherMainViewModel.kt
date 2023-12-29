package com.thelazybattley.feature.ui.mainscreen

import androidx.lifecycle.viewModelScope
import com.thelazybattley.domain.local.usecase.GetLocationUseCase
import com.thelazybattley.feature.base.BaseViewModel
import com.thelazybattley.feature.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherMainViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
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
