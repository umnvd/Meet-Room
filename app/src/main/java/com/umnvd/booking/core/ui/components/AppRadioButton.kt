package com.umnvd.booking.core.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.hint

@Composable
fun AppRadioButton(
    checked: Boolean,
    onChange: (Boolean) -> Unit,
) {
    IconButton(onClick = { onChange(!checked) }) {
        Crossfade(targetState = checked, label = "app_radio_button_anim") {
            if (it) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.hint,
                )
            }
        }
    }
}


@Preview
@Composable
private fun AppRadioButtonPreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.padding(16.dp)) {
            Column {
                AppRadioButton(checked = true, onChange = {})
                Spacer(modifier = Modifier.height(16.dp))
                AppRadioButton(checked = false, onChange = {})
            }
        }
    }
}