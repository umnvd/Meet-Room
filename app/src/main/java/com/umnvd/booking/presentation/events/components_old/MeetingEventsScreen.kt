package com.umnvd.booking.presentation.events.components_old

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.umnvd.booking.data.auth.services.AuthService
import kotlinx.coroutines.launch

@Composable
fun MeetingEventsScreen(
    onBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var signedOut by remember { mutableStateOf(false) }

    if (signedOut) {
        SideEffect {
            onBack()
            signedOut = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Text(text = "Events")
            Button(onClick = {
                coroutineScope.launch {
                    AuthService(Firebase.auth).signOut()
                    signedOut = true
                }
            }) {
                Text(text = "Sign Out")
            }
        }
    }
}