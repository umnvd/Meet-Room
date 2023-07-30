package com.umnvd.booking.presentation.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.components.AppTextField
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.hint
import com.umnvd.booking.core.ui.utils.text
import com.umnvd.booking.presentation.sign_in.viewmodel.SignInScreenState
import com.umnvd.booking.presentation.sign_in.viewmodel.SignInScreenViewModel

@Composable
fun SignInScreen(
    viewModel: SignInScreenViewModel = hiltViewModel(),
    onSignedIn: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val networkErrorMessage = stringResource(R.string.network_error)

    LaunchedEffect(state.signedIn) {
        if (state.signedIn) {
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

    LocalAppProgressIndicatorController.current.state(state.loading)

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
    state: SignInScreenState,
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
                    R.string.auth_screen_meeting_room_icon
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
                error = state.email.error?.text,
                enabled = state.fieldsEnabled,
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(
                placeholder = stringResource(R.string.auth_screen_password_hint),
                value = state.password.value,
                onValueChange = onPasswordChange,
                error = state.password.error?.text,
                visualTransformation = PasswordVisualTransformation(),
                enabled = state.fieldsEnabled
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = onSignInClick,
                enabled = state.buttonEnabled,
            ) {
                Text(text = stringResource(R.string.auth_screen_sign_in_button))
            }
        }
    }
}


@Preview
@Composable
private fun AuthScreenViewPreview() {
    MeetingRoomBookingTheme {
        AuthScreenContent(state = SignInScreenState())
    }
}

@Preview(locale = "ru")
@Composable
private fun AuthScreenViewPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        AuthScreenContent(state = SignInScreenState())
    }
}