package com.umnvd.booking.presentation.events.creation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MeetingEventCreationScreen(
    onCreated: () -> Unit,
    onBackClick: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "MeetingEventCreationScreen",
            Modifier.align(Alignment.Center)
        )
    }
}