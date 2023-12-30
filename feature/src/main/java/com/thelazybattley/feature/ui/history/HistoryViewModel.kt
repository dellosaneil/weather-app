package com.thelazybattley.feature.ui.history

import androidx.lifecycle.viewModelScope
import com.thelazybattley.domain.local.usecase.GetLocationUseCase
import com.thelazybattley.domain.network.usecase.GetHistoryUseCase
import com.thelazybattley.domain.usecase.LocalDateFormatterUseCase
import com.thelazybattley.domain.usecase.impl.LocalDateFormatterUseCaseImpl.Companion.YEAR_MONTH_DAY
import com.thelazybattley.feature.base.BaseViewModel
import com.thelazybattley.feature.di.IoDispatcher
import com.thelazybattley.feature.mapper.toData
import com.thelazybattley.feature.model.history.HistoryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val localDateFormatterUseCase: LocalDateFormatterUseCase
) : BaseViewModel<HistoryEvents, HistoryState>() {

    override fun initialState() = HistoryState()

    init {
        viewModelScope.launch(context = dispatcher) {
            getLocationUseCase().collect { location ->
                location?.let { loc ->
                    updateState { state ->
                        state.copy(
                            throwable = null,
                            isLoading = true
                        )
                    }
                    getHistoryUseCase(
                        latitude = loc.latitude.toString(),
                        longitude = loc.longitude.toString(),
                        startDate = localDateFormatterUseCase(
                            date = getCurrentState().startDate,
                            pattern = YEAR_MONTH_DAY
                        ),
                        endDate = localDateFormatterUseCase(
                            date = getCurrentState().endDate,
                            pattern = YEAR_MONTH_DAY
                        )
                    ).fold(
                        onSuccess = { schema ->
                            updateState { state ->
                                state.copy(
                                    throwable = null,
                                    historyData = schema.toData
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
        }
    }
}

sealed interface HistoryEvents {

}

data class HistoryState(
    val isLoading: Boolean = false,
    val historyData: HistoryData? = null,
    val startDate: LocalDate = LocalDate.now().minusMonths(3),
    val endDate: LocalDate = LocalDate.now().minusDays(1),
    val throwable: Throwable? = null
)
