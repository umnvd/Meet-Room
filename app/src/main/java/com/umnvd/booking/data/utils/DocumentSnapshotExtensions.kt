package com.umnvd.booking.data.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.umnvd.booking.data.rooms.models.MeetingRoomRemoteModel
import com.umnvd.booking.data.users.models.UserRemoteModel

fun DocumentSnapshot.toRoomRemote() =
    MeetingRoomRemoteModel(
        uid = id,
        name = getString(FirestoreContract.Rooms.NAME_KEY)!!,
        address = getString(FirestoreContract.Rooms.ADDRESS_KEY)!!,
        createdAt = getString(FirestoreContract.Rooms.CREATED_AT_KEY)!!
    )

fun DocumentSnapshot.toUserRemote() =
    UserRemoteModel(
        uid = id,
        nickname = getString(FirestoreContract.Users.NICKNAME_KEY)!!,
        fullName = getString(FirestoreContract.Users.FULL_NAME_KEY)!!,
        photoUrl = getString(FirestoreContract.Users.PHOTO_URL_KEY)!!,
    )