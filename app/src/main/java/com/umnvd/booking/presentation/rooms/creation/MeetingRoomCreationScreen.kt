package com.umnvd.booking.presentation.rooms.creation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MeetingRoomCreationScreen(
    onCreated: () -> Unit,
    onBackClick: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "MeetingRoomCreationScreen",
            Modifier.align(Alignment.Center)
        )
    }
}