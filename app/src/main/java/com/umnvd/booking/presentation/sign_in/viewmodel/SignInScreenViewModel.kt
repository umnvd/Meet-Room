package com.umnvd.booking.presentation.sign_in.viewmodel

import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.core.ui.viewmodel.BaseViewModel
import com.umnvd.booking.domain.auth.models.SignInResult
import com.umnvd.booking.domain.auth.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel<SignInScreenState>(SignInScreenState()) {

    fun setEmail(value: String) {
        updateState { it.copy(email = FieldState(value)) }
    }

    fun setPassword(value: String) {
        updateState { it.copy(password = FieldState(value)) }
    }

    fun signIn() {
        updateState { it.copy(loading = true) }
        viewModelScope.launch {
            val result = signInUseCase(
                SignInUseCase.Params(
                    email = currentState.value.email.value,
                    password = currentState.value.password.value,
                )
            )

            when (result) {
                is SignInResult.Success -> updateState { it.copy(signedIn = true) }
                is SignInResult.EmailError -> updateState {
                    it.copy(email = it.email.copy(error = result.error))
                }

                is SignInResult.PasswordError -> updateState {
                    it.copy(password = it.password.copy(error = result.error))
                }

                is SignInResult.NetworkError -> updateState { it.copy(networkError = true) }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun signedInHandled() {
        updateState { SignInScreenState() }
    }

    fun networkErrorHandled() {
        updateState { it.copy(networkError = false) }
    }
}