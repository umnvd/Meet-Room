package com.umnvd.booking.core.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    fieldModifier: Modifier = Modifier,
    error: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    multiline: Boolean = false,
    maxLines: Int = if (multiline) Int.MAX_VALUE else 1,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = fieldModifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            isError = error != null,
            enabled = enabled,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = !multiline,
            maxLines = maxLines,
            minLines = minLines,
            placeholder = placeholder?.let {
                {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = textStyle.fontSize,
                        )
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
            ),
        )
        error?.let {
            Text(
                text = it,
                style = LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.error,
                ),
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 2.dp
                ),
            )
        }
    }
}

@Preview
@Composable
private fun AppTextFieldPreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.fillMaxWidth()) {
            AppTextField(
                value = "Initial value",
                onValueChange = {},
                error = "Error message",
            )
        }
    }
}