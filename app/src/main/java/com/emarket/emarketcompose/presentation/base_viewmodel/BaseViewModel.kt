package com.emarket.emarketcompose.presentation.base_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        Event : com.emarket.emarketcompose.presentation.base_viewmodel.Event,
        State : com.emarket.emarketcompose.presentation.base_viewmodel.State,
        Effect : com.emarket.emarketcompose.presentation.base_viewmodel.Effect
        > : ViewModel() {

    private val initialState: State by lazy { setInitialState() }
    abstract fun setInitialState(): State

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private var _effect: Channel<Effect>? = null
    val effect get() = _effect?.receiveAsFlow()

    init {
        _effect = Channel()
        subscribeToEvents()
    }

    fun getCurrentState() = state.value

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: State.() -> State) {
        val newState = state.value.reducer()
        _state.value = newState
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    abstract fun handleEvents(event: Event)

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect?.send(effectValue) }
    }

    fun closeEffectChannel() {
        _effect?.close()
    }

    fun openEffectChannel() {
        _effect = Channel()
    }
}