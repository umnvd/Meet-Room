package com.umnvd.booking.presentation.profile.viewmodel

import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.ui.viewmodels.BaseViewModel
import com.umnvd.booking.domain.auth.usecases.SignOutUseCase
import com.umnvd.booking.domain.users.usecases.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<ProfileScreenState>(ProfileScreenState()) {

    init {
        loadProfile()
    }

    private fun loadProfile() {
        updateState { it.copy(loading = true) }
        viewModelScope.launch {
            when (val userResult = getCurrentUserUseCase()) {
                is Result.Success -> updateState { it.copy(user = userResult.value) }
                is Result.Error -> updateState { it.copy(error = userResult.error) }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun signOut() {
        updateState { it.copy(loading = true) }
        viewModelScope.launch {
            signOutUseCase()
            updateState { it.copy(signedOut = true) }
            updateState { it.copy(loading = false) }
        }
    }

    fun errorHandled() = updateState { it.copy(error = null) }
}