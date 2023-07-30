package com.umnvd.booking.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.umnvd.booking.R
import java.time.Month

val Month.text: String
    @Composable
    get() {
        val resId = when (this.value) {
            1 -> R.string.january
            2 -> R.string.february
            3 -> R.string.march
            4 -> R.string.april
            5 -> R.string.may
            6 -> R.string.june
            7 -> R.string.july
            8 -> R.string.august
            9 -> R.string.september
            10 -> R.string.october
            11 -> R.string.november
            else -> R.string.december
        }

        return stringResource(resId)
    }