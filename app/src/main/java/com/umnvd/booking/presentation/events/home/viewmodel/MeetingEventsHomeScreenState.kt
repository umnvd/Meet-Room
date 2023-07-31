package com.umnvd.booking.presentation.events.home.viewmodel

import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.models.MeetingEventModel
import java.time.LocalDate
import javax.annotation.concurrent.Immutable

@Immutable
data class MeetingEventsHomeScreenState(
    val date: LocalDate = LocalDate.now(),
    val events: List<MeetingEventModel> = listOf(),
    val loading: Boolean = false,
    val error: AppException? = null,
) {
    val eventDays: Set<LocalDate>
        get() = events.map {
            listOf(it.startAt.toLocalDate(), it.endAt.toLocalDate())
        }.flatten().toSet()

    val dayEvents: List<MeetingEventModel>
        get() = events.filter {
            it.startAt.toLocalDate() == date
                    || it.endAt.toLocalDate() == date
        }
}
