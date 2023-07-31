package com.umnvd.booking.presentation.events.common.form

import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import java.time.LocalDate
import java.time.LocalTime

interface MeetingEventFormController {
    fun setTitle(value: String)
    fun setDescription(value: String)
    fun setStartDate(value: LocalDate)
    fun setStartTime(value: LocalTime)
    fun setEndDate(value: LocalDate)
    fun setEndTime(value: LocalTime)
    fun setRoom(value: MeetingRoomModel)
    fun addUser(item: UserModel)
    fun removeUser(item: UserModel)
}