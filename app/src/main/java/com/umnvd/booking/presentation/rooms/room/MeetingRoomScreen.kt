package com.umnvd.booking.presentation.rooms.room

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MeetingRoomScreen(
    onEdited: () -> Unit,
    onBackClick: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "MeetingRoomScreen",
            Modifier.align(Alignment.Center)
        )
    }
}