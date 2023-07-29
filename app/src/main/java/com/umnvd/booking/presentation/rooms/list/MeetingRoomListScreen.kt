package com.umnvd.booking.presentation.rooms.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MeetingRoomListScreen(
    onRoomClick: (String) -> Unit,
    onCreateClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "MeetingRoomListScreen",
            Modifier.align(Alignment.Center)
        )
    }
}