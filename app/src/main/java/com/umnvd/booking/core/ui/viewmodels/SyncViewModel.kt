package com.umnvd.booking.core.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor() : ViewModel() {
    private val mutableSync = MutableStateFlow(false)

    val sync: StateFlow<Boolean> get() = mutableSync

    fun trigger() {
        mutableSync.value = true
    }

    fun syncHandled() {
        mutableSync.value = false
    }
}