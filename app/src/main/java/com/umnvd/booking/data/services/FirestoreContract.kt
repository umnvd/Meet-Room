package com.umnvd.booking.data.services

object FirestoreContract {
    object Users {
        const val COLLECTION_KEY = "users"

        const val NICKNAME_KEY = "nickname"
        const val FULL_NAME_KEY = "fullName"
        const val PHOTO_URL_KEY = "photoUrl"
    }

    object Rooms {
        const val COLLECTION_KEY = "rooms"

        const val NAME_KEY = "name"
        const val ADDRESS_KEY = "address"
    }

    object Events {
        const val COLLECTION_KEY = "events"

        const val TITLE_KEY = "title"
        const val DESCRIPTION_KEY = "description"
        const val START_AT_KEY = "startAt"
        const val END_AT_KEY = "endAt"
        const val ROOM_KEY = "room"
        const val PARTICIPANTS_KEY = "participants"
    }
}