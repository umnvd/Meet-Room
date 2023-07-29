package com.umnvd.booking.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme

val LocalAppProgressIndicatorController =
    compositionLocalOf { AppProgressIndicatorController { } }

class AppProgressIndicatorController(
    private val setState: (Boolean) -> Unit,
) {
    fun show() = setState(true)

    fun hide() = setState(true)

    fun state(value: Boolean) = setState(value)
}

@Composable
fun AppProgressIndicator(
    loading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()
        AnimatedVisibility(
            visible = loading,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.small,
                        )
                        .align(Alignment.Center)
                ) {
                    CircularProgressIndicator(
                        strokeWidth = 4.dp,
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun AppScreenProgressIndicatorPreview() {
    MeetingRoomBookingTheme {
        Surface {
            AppProgressIndicator(
                loading = true,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text("Test", Modifier.align(Alignment.Center))
            }
        }
    }
}