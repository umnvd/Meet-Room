package com.umnvd.booking.domain.repositories

import com.umnvd.booking.domain.models.MeetingEvent
import com.umnvd.booking.domain.models.MeetingEventForm

interface MeetingEventsRepository {

    suspend fun event(uid: String): MeetingEvent

    suspend fun allEvents(): List<MeetingEvent>

    suspend fun createEvent(form: MeetingEventForm): MeetingEvent

    suspend fun editEvent(uid: String, form: MeetingEventForm): MeetingEvent
    suspend fun deleteEvent(uid: String): Unit
}