package com.umnvd.booking.presentation.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.models.FieldState
import com.umnvd.booking.domain.errors.EmailInvalidException
import com.umnvd.booking.domain.errors.EmailNotRegisteredException
import com.umnvd.booking.domain.errors.EmailRequiredException
import com.umnvd.booking.domain.errors.NetworkException
import com.umnvd.booking.domain.errors.PasswordInvalidException
import com.umnvd.booking.domain.errors.PasswordMinLengthException
import com.umnvd.booking.domain.errors.PasswordRequiredException
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
            try {
                signInUseCase(
                    SignInUseCase.Params(
                        email = _state.value.email.value,
                        password = _state.value.password.value,
                    )
                )
                _state.update { it.copy(signedIn = true) }
                // TODO: error handling
            } catch (e: EmailRequiredException) {
                _state.update {
                    it.copy(email = it.email.copy(error = EmailFieldError.Required))
                }
            } catch (e: EmailInvalidException) {
                _state.emit(
                    _state.value.copy(
                        email = _state.value.email.copy(error = EmailFieldError.Invalid)
                    )
                )
            } catch (e: EmailNotRegisteredException) {
                _state.emit(
                    _state.value.copy(
                        email = _state.value.email.copy(error = EmailFieldError.NotRegistered)
                    )
                )
            } catch (e: PasswordRequiredException) {
                _state.emit(
                    _state.value.copy(
                        password = _state.value.password.copy(error = PasswordFieldError.Required),
                    )
                )
            } catch (e: PasswordMinLengthException) {
                _state.emit(
                    _state.value.copy(
                        password = _state.value.password.copy(
                            error = PasswordFieldError.TooShort(e.minLength)
                        ),
                    )
                )
            } catch (e: PasswordInvalidException) {
                _state.emit(
                    _state.value.copy(
                        password = _state.value.password.copy(error = PasswordFieldError.Invalid),
                    )
                )
            } catch (e: NetworkException) {
                Log.d("MMM", "ViewModel : NetworkException")
                _state.emit(_state.value.copy(networkError = true))
            } finally {
                _state.emit(_state.value.copy(loading = false))
            }
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