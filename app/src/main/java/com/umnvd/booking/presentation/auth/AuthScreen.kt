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
    val networkErrorMessage = stringResource(R.string.network_error)

    if (state.signedIn) {
        SideEffect {
            onSignedIn()
            viewModel.signedInHandled()
        }
    }


    LaunchedEffect(state.networkError) {
        if (state.networkError) {
            snackbarHostState.showSnackbar(networkErrorMessage)
            viewModel.networkErrorHandled()
        }
    }

    // Preview не может инициализировать вьюмодель
    AuthScreenContent(
        state = state,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        onEmailChange = viewModel::setEmail,
        onPasswordChange = viewModel::setPassword,
        onSignInClick = viewModel::signIn,
    )
}

@Composable
private fun AuthScreenContent(
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
                imageVector = Icons.Outlined.MeetingRoom,
                contentDescription = stringResource(
                    R.string.auth_screen_meeting_room_icon_description
                ),
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
                placeholder = stringResource(R.string.auth_screen_email_hint),
                value = state.email.value,
                onValueChange = onEmailChange,
                error = state.email.error?.let {
                    when (it) {
                        is EmailFieldError.Required ->
                            stringResource(R.string.auth_screen_email_required_error)

                        is EmailFieldError.Invalid ->
                            stringResource(R.string.auth_screen_email_invalid_error)

                        is EmailFieldError.NotRegistered ->
                            stringResource(R.string.auth_screen_email_not_registered_error)
                    }
                },
                enabled = state.fieldsEnabled,
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(
                placeholder = stringResource(R.string.auth_screen_password_hint),
                value = state.password.value,
                onValueChange = onPasswordChange,
                error = state.password.error?.let {
                    when (it) {
                        is PasswordFieldError.Required ->
                            stringResource(R.string.auth_screen_password_required_error)

                        is PasswordFieldError.TooShort ->
                            stringResource(
                                R.string.auth_screen_password_too_short_error,
                                it.minLength
                            )

                        is PasswordFieldError.Invalid ->
                            stringResource(R.string.auth_screen_password_invalid_error)
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
                        Text(text = stringResource(R.string.auth_screen_sign_in_button))
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
        AuthScreenContent(state = AuthScreenState())
    }
}

@Preview(locale = "ru")
@Composable
private fun AuthScreenViewPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        AuthScreenContent(state = AuthScreenState())
    }
}