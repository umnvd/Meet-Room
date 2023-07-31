package com.umnvd.booking.presentation

import com.umnvd.booking.core.ui.viewmodels.BaseViewModel
import com.umnvd.booking.domain.auth.models.AuthState
import com.umnvd.booking.domain.auth.usecases.CheckAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    checkAuthUseCase: CheckAuthUseCase,
) : BaseViewModel<AuthState>(AuthState.UNKNOWN) {

    init {
        updateState { checkAuthUseCase.invoke() }
    }
}