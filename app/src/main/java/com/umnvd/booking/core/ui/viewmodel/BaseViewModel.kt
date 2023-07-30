package com.umnvd.booking.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<T>(
    private val initialState: T
) : ViewModel() {

    private val mutableState = MutableStateFlow(initialState)
    val state: StateFlow<T> get() = mutableState

    protected fun updateState(builder: (T) -> T) = mutableState.update(builder)

    protected fun resetState() = mutableState.update { initialState }
}