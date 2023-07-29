package com.umnvd.booking.presentation.events.schedule.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MeetingEventScheduleScreen(
    onEventCLick: (String) -> Unit,
    onMonthClick: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "MeetingEventScheduleScreen",
            Modifier.align(Alignment.Center)
        )
    }
}