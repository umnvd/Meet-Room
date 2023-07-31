package com.umnvd.booking.presentation.sign_in.viewmodel

import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.core.ui.viewmodel.BaseViewModel
import com.umnvd.booking.domain.auth.models.SignInError
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
                    email = state.value.email.value,
                    password = state.value.password.value,
                )
            )

            when (result) {
                is Result.Success -> updateState { it.copy(signedIn = true) }
                is Result.Error -> when (result.error) {
                    is SignInError.Validation -> updateState {
                        it.copy(
                            email = it.email.copy(error = result.error.email),
                            password = it.password.copy(error = result.error.password),
                        )
                    }

                    is SignInError.Common ->
                        updateState { it.copy(error = result.error.error) }
                }

            }
            updateState { it.copy(loading = false) }
        }
    }

    fun errorHandled() = updateState { it.copy(error = null) }
}