package com.umnvd.booking.presentation.events.event

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MeetingEventScreen(
    onEdited: () -> Unit,
    onBackCLick: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "MeetingEventScreen",
            Modifier.align(Alignment.Center)
        )
    }
}