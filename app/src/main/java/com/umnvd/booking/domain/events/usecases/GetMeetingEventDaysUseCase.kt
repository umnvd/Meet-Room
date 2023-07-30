package com.umnvd.booking.domain.events.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import java.time.LocalDate
import javax.inject.Inject

class GetMeetingEventDaysUseCase @Inject constructor(
    private val meetingEventsRepository: MeetingEventsRepository,
) {

    suspend operator fun invoke(): Result<Set<LocalDate>, AppException> {
        val events = meetingEventsRepository.allEvents()
        val days = events.map {
            listOf(it.startAt.toLocalDate(), it.endAt.toLocalDate())
        }.flatten().toSet()

        return Result.Success(days)
    }
}