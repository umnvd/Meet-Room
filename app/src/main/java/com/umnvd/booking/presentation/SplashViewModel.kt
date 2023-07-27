package com.umnvd.booking.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState.UNKNOWN)
    val authState = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            val user = firebaseAuth.currentUser
            Log.d("MMM_AUTH", "Auth: ${user != null}, FBUser: $user")
            _authState.value = if (user == null) AuthState.UNAUTHORIZED else AuthState.AUTHORIZED
        }
    }
}

enum class AuthState {
    UNKNOWN,
    AUTHORIZED,
    UNAUTHORIZED,
}