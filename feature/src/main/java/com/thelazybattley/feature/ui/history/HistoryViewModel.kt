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
import kotlin.math.roundToInt

@HiltViewModel
class HistoryViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val localDateFormatterUseCase: LocalDateFormatterUseCase
) : BaseViewModel<HistoryEvents, HistoryState>(), HistoryCallbacks {

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
                                val data = schema.toData
                                val selectedData = data.daily.temperature2mMean
                                state.copy(
                                    throwable = null,
                                    historyData = data,
                                    selectedData = selectedData,
                                    yAxisMinValue = selectedData.minOf { it },
                                    yAxisMaxValue = selectedData.maxOf { it },
                                    startHighlightedIndex = 0,
                                    endHighlightedIndex = selectedData.size
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

    override fun selectLegend(legend: HistoryLegend) {
        viewModelScope.launch(context = dispatcher) {
            val historyData = getCurrentState().historyData?.daily ?: return@launch
            val selectedData = when (legend) {
                HistoryLegend.MEAN_TEMPERATURE -> historyData.temperature2mMean
                HistoryLegend.MAX_TEMPERATURE -> historyData.temperature2mMax
                HistoryLegend.PRECIPITATION_SUM -> historyData.precipitationSum
                HistoryLegend.MIN_TEMPERATURE -> historyData.temperature2mMin
            }

            updateState { state ->
                state.copy(
                    selectedLegend = legend,
                    selectedData = selectedData.subList(
                        fromIndex = state.startHighlightedIndex,
                        toIndex = state.endHighlightedIndex
                    ),
                    yAxisMaxValue = selectedData.maxOf { it },
                    yAxisMinValue = selectedData.minOf { it }
                )
            }
        }
    }

    override fun highlightData(xStartOffset: Float, xEndOffset: Float, chartWidth: Float) {
        val itemCount = getCurrentState().selectedData.size

        val startIndexPercentage = xStartOffset / chartWidth
        val endIndexPercentage = (xStartOffset + xEndOffset) / chartWidth

        val startIndex = (startIndexPercentage * itemCount).roundToInt()
        val endIndex = (endIndexPercentage * itemCount).roundToInt()

        val (start, end) = when {
            startIndex == 0 && endIndex == 0 -> 0 to itemCount
            (startIndex < endIndex) -> startIndex.coerceAtLeast(minimumValue = 0) to endIndex.coerceAtMost(maximumValue = itemCount)
            else -> endIndex.coerceAtLeast(minimumValue = 0) to startIndex.coerceAtMost(maximumValue = itemCount)
        }
        viewModelScope.launch(context = dispatcher) {
            updateState { state ->
                state.copy(
                    startHighlightedIndex = start,
                    endHighlightedIndex = end,
                    selectedData = state
                        .selectedData
                        .subList(
                            fromIndex = start,
                            toIndex = end
                        )
                )
            }
        }
    }
}

sealed interface HistoryEvents {

}

data class HistoryState(
    val isLoading: Boolean = false,
    val historyData: HistoryData? = null,
    val startDate: LocalDate = LocalDate.now().minusWeeks(10),
    val endDate: LocalDate = LocalDate.now().minusDays(1),
    val throwable: Throwable? = null,
    val selectedData: List<Double> = emptyList(),
    val selectedLegend: HistoryLegend = HistoryLegend.MEAN_TEMPERATURE,
    val yAxisMaxValue: Double = Double.MAX_VALUE,
    val yAxisMinValue: Double = Double.MIN_VALUE,
    val startHighlightedIndex: Int = 0,
    val endHighlightedIndex: Int = 0
)
