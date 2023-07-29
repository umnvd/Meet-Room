package com.umnvd.booking.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(
    onSignedOut: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "ProfileScreen",
            Modifier.align(Alignment.Center)
        )
    }
}