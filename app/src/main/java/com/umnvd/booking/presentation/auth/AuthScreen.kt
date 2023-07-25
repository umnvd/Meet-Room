package com.umnvd.booking.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umnvd.booking.core.ui.components.AppTextField
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.presentation.auth.viewmodel.AuthScreenViewModel

@Composable
fun AuthScreen(
    viewModel: AuthScreenViewModel = hiltViewModel(),
    onSignedIn: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    fun onSignInButtonClick() {
        viewModel.signIn()
    }

    if (state.signedIn) {
        SideEffect {
            onSignedIn()
            viewModel.handleSignedIn()
        }
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp),
        ) {
            AppTextField(
                placeholder = "Email",
                value = state.login.value,
                onValueChange = viewModel::setLogin,
                error = state.login.error,
                enabled = state.fieldsEnabled,
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(
                placeholder = "Password",
                value = state.password.value,
                onValueChange = viewModel::setPassword,
                error = state.password.error,
                visualTransformation = PasswordVisualTransformation(),
                enabled = state.fieldsEnabled
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                Modifier.height(48.dp)
            ) {
                if (state.loading) {
                    CircularProgressIndicator(
                        strokeWidth = 4.dp,
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier.height(36.dp)
                    )
                } else {
                    TextButton(
                        onClick = ::onSignInButtonClick,
                        enabled = state.buttonEnabled,
                        elevation = null,
                        modifier = Modifier.defaultMinSize(
                            // TODO: defaults
                            minHeight = 48.dp,
                            minWidth = 224.dp,
                        )
                    ) {
                        Text(text = "Sign In")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    MeetingRoomBookingTheme {
        AuthScreen(onSignedIn = {})
    }
}