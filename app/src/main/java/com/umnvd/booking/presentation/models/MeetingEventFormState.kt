package com.umnvd.booking.presentation.models

import com.umnvd.booking.core.models.FieldState
import com.umnvd.booking.domain.models.MeetingRoom
import com.umnvd.booking.domain.models.User
import java.time.LocalDate
import java.time.LocalTime

data class MeetingEventFormState(
    val title: FieldState<String, String>,
    val description: FieldState<String, String>,
    val startDate: FieldState<LocalDate, String>,
    val startTime: FieldState<LocalTime, String>,
    val endDate: FieldState<LocalDate, String>,
    val endTime: FieldState<LocalTime, String>,
    val room: FieldState<MeetingRoom?, String>,
    val participants: FieldState<List<User>, String>,
) {

    companion object {
        val Default: MeetingEventFormState
            get() {
                val defaultDate = LocalDate.now()
                val defaultStartTime = LocalTime.of(12, 0)
                val defaultEndTime = LocalTime.of(13, 0)

                return MeetingEventFormState(
                    title = FieldState(""),
                    description = FieldState(""),
                    startDate = FieldState(defaultDate),
                    startTime = FieldState(defaultStartTime),
                    endDate = FieldState(defaultDate),
                    endTime = FieldState(defaultEndTime),
                    room = FieldState(null),
                    participants = FieldState(listOf()),
                )
            }
    }
}
