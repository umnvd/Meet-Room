package com.umnvd.booking.presentation.mappers

import com.umnvd.booking.domain.models.MeetingEventForm
import com.umnvd.booking.presentation.models.MeetingEventFormState
import java.time.LocalDateTime

object MeetingEventUiMapper {

    fun formStateToDomain(obj: MeetingEventFormState) = MeetingEventForm(
        title = obj.title.value,
        description = obj.description.value,
        startAt = LocalDateTime.of(obj.startDate.value, obj.startTime.value),
        endAt = LocalDateTime.of(obj.endDate.value, obj.endTime.value),
        room = obj.room.value,
        participants = obj.participants.value,
    )
}