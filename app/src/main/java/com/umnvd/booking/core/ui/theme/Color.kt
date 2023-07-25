package com.umnvd.booking.core.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object MeetingRoomBookingColors {
    val White = Color(0xFFFDFBFF)
    val Black = Color(0xFF1D1B1F)
    val Purple400 = Color(0xFF8A5FA0)
    val Purple800 = Color(0xFF5C3178)
    val Green400 = Color(0xFF5FA08A)
    val Green800 = Color(0xFF406353)
    val Yellow900 = Color(0xFFA08A5F)

    // TODO: change
    val Gray400 = Color(0xFF8B8B8B)
    val Gray300 = Color(0xFFEEEEEE)
    val Red600 = Color(0xFFEE2A2A)
}

val LightColors = lightColors(
    primary = MeetingRoomBookingColors.Purple400,
    secondary = MeetingRoomBookingColors.Green400,
    onPrimary = MeetingRoomBookingColors.White,
    onSecondary = MeetingRoomBookingColors.Black,
    background = MeetingRoomBookingColors.White,
    surface = MeetingRoomBookingColors.White,
    onBackground = MeetingRoomBookingColors.Black,
    onSurface = MeetingRoomBookingColors.Black,
    error = MeetingRoomBookingColors.Red600,
    onError = MeetingRoomBookingColors.White
)

val DarkColors = darkColors(
    primary = MeetingRoomBookingColors.Purple800,
    secondary = MeetingRoomBookingColors.Green800,
    onPrimary = MeetingRoomBookingColors.White,
    onSecondary = MeetingRoomBookingColors.White,
    background = MeetingRoomBookingColors.Black,
    surface = MeetingRoomBookingColors.Black,
    onBackground = MeetingRoomBookingColors.White,
    onSurface = MeetingRoomBookingColors.White,
    error = MeetingRoomBookingColors.Red600,
    onError = MeetingRoomBookingColors.White
)
