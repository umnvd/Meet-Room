package com.umnvd.booking.core.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

object MeetingRoomBookingColors {
    val White = Color(0xFFFDFBFF)
    val Black = Color(0xFF1D1B1F)
    val Purple400 = Color(0xFF8A5FA0)
    val Purple800 = Color(0xFF5C3178)
    val Green400 = Color(0xFF5FA08A)
    val Green800 = Color(0xFF406353)
    val Yellow900 = Color(0xFFA08A5F)

    val Gray800 = Color(0xFF424242)
    val Gray600 = Color(0xFF757575)
    val Gray400 = Color(0xFFBDBDBD)
    val Gray200 = Color(0xFFEEEEEE)
    val Red600 = Color(0xFFEE2A2A)
}

val LightColors = lightColorScheme(
    primary = MeetingRoomBookingColors.Purple400,
    secondary = MeetingRoomBookingColors.Green400,
    tertiary = MeetingRoomBookingColors.Yellow900,
    onPrimary = MeetingRoomBookingColors.White,
    onSecondary = MeetingRoomBookingColors.White,
    background = MeetingRoomBookingColors.White,
    surface = MeetingRoomBookingColors.White,
    onBackground = MeetingRoomBookingColors.Black,
    onSurface = MeetingRoomBookingColors.Black,
    error = MeetingRoomBookingColors.Red600,
    onError = MeetingRoomBookingColors.White,
)

val DarkColors = darkColorScheme(
    primary = MeetingRoomBookingColors.Purple800,
    secondary = MeetingRoomBookingColors.Green800,
    tertiary = MeetingRoomBookingColors.Yellow900,
    onPrimary = MeetingRoomBookingColors.White,
    onSecondary = MeetingRoomBookingColors.White,
    background = MeetingRoomBookingColors.Black,
    surface = MeetingRoomBookingColors.Black,
    onBackground = MeetingRoomBookingColors.White,
    onSurface = MeetingRoomBookingColors.White,
    error = MeetingRoomBookingColors.Red600,
    onError = MeetingRoomBookingColors.White,
)

val ColorScheme.isLight: Boolean
    get() = background.luminance() > 0.5

val ColorScheme.hint: Color
    get() =
        if (isLight) MeetingRoomBookingColors.Gray600
        else MeetingRoomBookingColors.Gray400

val ColorScheme.hintOnSecondary: Color
    get() =
        if (isLight) MeetingRoomBookingColors.Gray200
        else MeetingRoomBookingColors.Gray400

val ColorScheme.divider: Color
    get() =
        if (isLight) MeetingRoomBookingColors.Gray200
        else MeetingRoomBookingColors.Gray800

