package com.umnvd.booking.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.components.AppTextField
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.hint
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

    Surface{
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
        ) {
            Icon(
                // painter = painterResource(id = R.drawable.ic_launcher_foreground),
                imageVector = Icons.Outlined.MeetingRoom,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
             Text(
                 text = stringResource(id = R.string.app_name),
                 style = MaterialTheme.typography.headlineLarge,
                 color = MaterialTheme.colorScheme.hint,
             )
            Spacer(modifier = Modifier.height(64.dp))
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
            Spacer(modifier = Modifier.height(16.dp))
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

@Preview
@Composable
private fun AuthScreenPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        AuthScreen(onSignedIn = {})
    }
}