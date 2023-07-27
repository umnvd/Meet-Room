package com.umnvd.booking.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
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
import com.umnvd.booking.presentation.auth.viewmodel.AuthScreenState
import com.umnvd.booking.presentation.auth.viewmodel.AuthScreenViewModel
import com.umnvd.booking.presentation.auth.viewmodel.EmailFieldError
import com.umnvd.booking.presentation.auth.viewmodel.PasswordFieldError

@Composable
fun AuthScreen(
    viewModel: AuthScreenViewModel = hiltViewModel(),
    onSignedIn: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    if (state.signedIn) {
        SideEffect {
            onSignedIn()
            viewModel.signedInHandled()
        }
    }


    LaunchedEffect(state.networkError) {
        if (state.networkError) {
            snackbarHostState.showSnackbar("Network error")
            viewModel.networkErrorHandled()
        }
    }

    AuthScreenView(
        state = state,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        onEmailChange = viewModel::setEmail,
        onPasswordChange = viewModel::setPassword,
        onSignInClick = viewModel::signIn,
    )
}

@Composable
private fun AuthScreenView(
    state: AuthScreenState,
    modifier: Modifier = Modifier,
    snackbarHost: @Composable () -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onSignInClick: () -> Unit = {},
) {
    Scaffold(
        snackbarHost = snackbarHost,
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp),
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
                value = state.email.value,
                onValueChange = onEmailChange,
                error = state.email.error?.let {
                    when (it) {
                        is EmailFieldError.Required -> "Email is required"
                        is EmailFieldError.Invalid -> "Email is invalid"
                        is EmailFieldError.NotRegistered -> "User with this email is not registered"
                    }
                },
                enabled = state.fieldsEnabled,
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(
                placeholder = "Password",
                value = state.password.value,
                onValueChange = onPasswordChange,
                error = state.password.error?.let {
                    when (it) {
                        // TODO: String resources
                        is PasswordFieldError.Required -> "Password is required"
                        is PasswordFieldError.TooShort -> "Password must have at least ${it.minLength}"
                        is PasswordFieldError.Invalid -> "Password is invalid"
                    }
                },
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
                        onClick = onSignInClick,
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
private fun AuthScreenViewPreview() {
    MeetingRoomBookingTheme {
        AuthScreenView(state = AuthScreenState())
    }
}

@Preview
@Composable
private fun AuthScreenViewPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        AuthScreenView(state = AuthScreenState())
    }
}