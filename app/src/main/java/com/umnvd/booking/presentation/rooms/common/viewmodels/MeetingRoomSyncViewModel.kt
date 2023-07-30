package com.umnvd.booking.presentation.rooms.common.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MeetingRoomSyncViewModel @Inject constructor() : ViewModel() {
    private val mutableSync = MutableStateFlow(false)

    val sync: StateFlow<Boolean> get() = mutableSync

    fun triggerSync() {
        Log.d("ROOM_SYNC", "$this - triggerSync")
        mutableSync.value = true
    }

    fun syncHandled() {
        Log.d("ROOM_SYNC", "$this - syncHandled")
        mutableSync.value = false
    }
}