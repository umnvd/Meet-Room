package com.umnvd.booking.domain.events.repositories

import com.umnvd.booking.domain.events.models.MeetingEventFormModel
import com.umnvd.booking.domain.events.models.MeetingEventModel

interface MeetingEventsRepository {
    suspend fun event(uid: String): MeetingEventModel
    suspend fun allEvents(): List<MeetingEventModel>
    suspend fun createEvent(form: MeetingEventFormModel): MeetingEventModel
    suspend fun editEvent(uid: String, form: MeetingEventFormModel): MeetingEventModel
    suspend fun deleteEvent(uid: String)
}