package com.umnvd.booking.domain.repositories

import com.umnvd.booking.domain.models.MeetingEvent
import com.umnvd.booking.domain.models.User
import java.time.LocalDate

interface MeetingEventsRepository {
    suspend fun eventsByDate(date: LocalDate): List<MeetingEvent>

    suspend fun eventsByUser(user: User): List<MeetingEvent>

    suspend fun allEvents(): List<MeetingEvent>
}