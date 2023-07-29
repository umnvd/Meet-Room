package com.umnvd.booking.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<T>(
    initialState: T
) : ViewModel() {

    protected val currentState = MutableStateFlow(initialState)
    val state: StateFlow<T> get() = currentState

    protected fun updateState(builder: (T) -> T) = currentState.update(builder)
}