package com.umnvd.booking.presentation.events.schedule.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MeetingEventCalendarScreen(
    onBackCLick: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "MeetingEventCalendarScreen",
            Modifier.align(Alignment.Center)
        )
    }
}