package com.umnvd.booking.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.models.FieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(AuthScreenState())
    val state: StateFlow<AuthScreenState> = _state

    fun setLogin(value: String) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(login = FieldState(value)))
        }
    }

    fun setPassword(value: String) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(password = FieldState(value)))
        }
    }

    fun signIn() {
        viewModelScope.launch {
            _state.emit(_state.value.copy(loading = true))
            delay(1500)
            _state.emit(
                _state.value.copy(
                    signedIn = true,
                )
            )
        }
    }

    fun handleSignedIn() {
        viewModelScope.launch {
            _state.emit(AuthScreenState())
        }
    }
}