package com.umnvd.booking.presentation.events.common.form

import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.domain.events.models.MeetingEventFormModel
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class MeetingEventFormState(
    val title: FieldState<String> = FieldState(""),
    val description: FieldState<String> = FieldState(""),
    val startDate: FieldState<LocalDate> = FieldState(LocalDate.now()),
    val startTime: FieldState<LocalTime> = FieldState(LocalTime.of(12, 0)),
    val endDate: FieldState<LocalDate> = FieldState(LocalDate.now()),
    val endTime: FieldState<LocalTime> = FieldState(LocalTime.of(13, 0)),
    val room: FieldState<MeetingRoomModel?> = FieldState(null),
    val participants: FieldState<List<UserModel>> = FieldState(listOf()),
)

fun MeetingEventFormState.toDomain() = MeetingEventFormModel(
    title = title.value,
    description = description.value,
    startAt = LocalDateTime.of(startDate.value, startTime.value),
    endAt = LocalDateTime.of(endDate.value, endTime.value),
    room = room.value,
    participants = participants.value,
)

fun MeetingEventModel.toForm() = MeetingEventFormModel(
    title = title,
    description = description,
    startAt = startAt,
    endAt = endAt,
    room = room,
    participants = participants,
)

fun MeetingEventModel.toFormState() = MeetingEventFormState(
    title = FieldState(title),
    description = FieldState(description),
    startDate = FieldState(startAt.toLocalDate()),
    startTime = FieldState(startAt.toLocalTime()),
    endDate = FieldState(endAt.toLocalDate()),
    endTime = FieldState(endAt.toLocalTime()),
    room = FieldState(room),
    participants = FieldState(participants),
)