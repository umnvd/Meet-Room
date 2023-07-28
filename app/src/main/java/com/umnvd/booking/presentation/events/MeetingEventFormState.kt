package com.umnvd.booking.presentation.events

import com.umnvd.booking.core.models.FieldState
import com.umnvd.booking.domain.events.models.MeetingEventFormModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class MeetingEventFormState(
    val title: FieldState<String, String>,
    val description: FieldState<String, String>,
    val startDate: FieldState<LocalDate, String>,
    val startTime: FieldState<LocalTime, String>,
    val endDate: FieldState<LocalDate, String>,
    val endTime: FieldState<LocalTime, String>,
    val room: FieldState<MeetingRoomModel?, String>,
    val participants: FieldState<List<UserModel>, String>,
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

fun MeetingEventFormState.toDomain() = MeetingEventFormModel(
    title = title.value,
    description = description.value,
    startAt = LocalDateTime.of(startDate.value, startTime.value),
    endAt = LocalDateTime.of(endDate.value, endTime.value),
    room = room.value,
    participants = participants.value,
)