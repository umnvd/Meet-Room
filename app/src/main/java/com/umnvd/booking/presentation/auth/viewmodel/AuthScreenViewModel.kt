package com.umnvd.booking.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.models.FieldState
import com.umnvd.booking.domain.auth.models.SignInResult
import com.umnvd.booking.domain.auth.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AuthScreenState())
    val state: StateFlow<AuthScreenState> = _state

    fun setEmail(value: String) {
        _state.update { it.copy(email = FieldState(value)) }
    }

    fun setPassword(value: String) {
        _state.update { it.copy(password = FieldState(value)) }
    }

    fun signIn() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            val result = signInUseCase(
                SignInUseCase.Params(
                    email = _state.value.email.value,
                    password = _state.value.password.value,
                )
            )

            when (result) {
                is SignInResult.Success -> _state.update { it.copy(signedIn = true) }
                is SignInResult.EmailError -> _state.update {
                    it.copy(email = it.email.copy(error = result.error))
                }

                is SignInResult.PasswordError -> _state.update {
                    it.copy(password = it.password.copy(error = result.error))
                }

                is SignInResult.NetworkError -> _state.update { it.copy(networkError = true) }
            }
            _state.update { it.copy(loading = false) }
        }
    }

    fun signedInHandled() {
        viewModelScope.launch {
            _state.emit(AuthScreenState())
        }
    }

    fun networkErrorHandled() {
        viewModelScope.launch {
            _state.emit(_state.value.copy(networkError = false))
        }
    }
}