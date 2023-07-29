package com.umnvd.booking.presentation.events.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun UserMeetingEventListScreen(
    onEventClick: (String) -> Unit,
    onBackCLick: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "UserMeetingEventListScreen",
            Modifier.align(Alignment.Center)
        )
    }
}