package com.umnvd.booking.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.components.LocalAppErrorSnackbarController
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.hint
import com.umnvd.booking.core.ui.utils.debugPlaceholder
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.presentation.profile.viewmodel.ProfileScreenViewModel
import com.umnvd.booking.util.PreviewMocks

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    onSignedOut: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)
    LocalAppErrorSnackbarController.current.show(state.error, viewModel::errorHandled)

    LaunchedEffect(state.signedOut) { if (state.signedOut) { onSignedOut() } }

    ProfileScreenContent(
        user = state.user,
        onSignOutClick = viewModel::signOut
    )
}

@Composable
private fun ProfileScreenContent(
    user: UserModel?,
    onSignOutClick: () -> Unit = {},
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        if (user != null) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = user.photoUrl,
                        contentDescription = stringResource(R.string.profile_avatar_description),
                        placeholder = debugPlaceholder(R.drawable.mock_user_avatar),
                        modifier = Modifier
                            .size(96.dp)
                            .clip(RoundedCornerShape(percent = 50))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = user.fullName,
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = user.nickname,
                            color = MaterialTheme.colorScheme.hint
                        )
                    }
                }
                Button(onClick = onSignOutClick) {
                    Text(text = stringResource(R.string.profile_sign_out_button))
                }
            }
        }
    }
}


@Preview
@Composable
fun ProfileScreenContentPreview() {
    MeetingRoomBookingTheme {
        ProfileScreenContent(
            user = PreviewMocks.Users().user
        )
    }
}