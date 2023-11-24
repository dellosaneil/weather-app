package com.dellosaneil.feature.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update


abstract class BaseViewModel<Event : Any, ViewState : Any> : ViewModel() {

    private val initialState: ViewState by lazy {
        initialState()
    }

    abstract fun initialState(): ViewState

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(value = initialState)

    val state: StateFlow<ViewState> = _state

    suspend fun emitEvent(event: Event) {
        _events.send(element = event)
    }

    fun updateState(newState: (ViewState) -> ViewState) {
        _state.update(newState)
    }

    fun getCurrentState() = _state.value


    override fun onCleared() {
        super.onCleared()
        _events.close()
    }

}

