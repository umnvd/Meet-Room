package com.umnvd.booking.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

@Composable
fun debugPlaceholder(@DrawableRes drawableResId: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = drawableResId)
    } else {
        null
    }