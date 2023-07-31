package com.umnvd.booking.data.rooms.services

import com.google.firebase.firestore.FirebaseFirestore
import com.umnvd.booking.data.common.mappers.DateTimeMapper
import com.umnvd.booking.data.rooms.models.MeetingRoomFormRemoteModel
import com.umnvd.booking.data.rooms.models.MeetingRoomRemoteModel
import com.umnvd.booking.data.utils.FirestoreContract
import com.umnvd.booking.data.utils.toRoomRemote
import com.umnvd.booking.data.utils.withFBExceptionMapper
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject

class MeetingRoomsService @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) {

    suspend fun getRoom(uid: String): MeetingRoomRemoteModel = withFBExceptionMapper {
        return@withFBExceptionMapper firebaseFirestore
            .collection(FirestoreContract.Rooms.COLLECTION_KEY)
            .document(uid)
            .get()
            .await()
            .toRoomRemote()
    }

    suspend fun getRooms(): List<MeetingRoomRemoteModel> = withFBExceptionMapper {
        return@withFBExceptionMapper firebaseFirestore
            .collection(FirestoreContract.Rooms.COLLECTION_KEY)
            .get()
            .await()
            .documents
            .map { it.toRoomRemote() }
    }

    suspend fun createRoom(data: MeetingRoomFormRemoteModel): MeetingRoomRemoteModel =
        withFBExceptionMapper {
            val createdAt = DateTimeMapper.ldtToUtcString(LocalDateTime.now())

            val roomReference = firebaseFirestore
                .collection(FirestoreContract.Rooms.COLLECTION_KEY)
                .add(data.toDocumentData() + (FirestoreContract.Rooms.CREATED_AT_KEY to createdAt))
                .await()

            return@withFBExceptionMapper getRoom(roomReference.id)
        }

    suspend fun editRoom(uid: String, data: MeetingRoomFormRemoteModel): MeetingRoomRemoteModel =
        withFBExceptionMapper {
            firebaseFirestore
                .collection(FirestoreContract.Rooms.COLLECTION_KEY)
                .document(uid)
                .update(data.toDocumentData())
                .await()

            return@withFBExceptionMapper getRoom(uid)
        }

    suspend fun deleteRoom(uid: String): Unit = withFBExceptionMapper {
        firebaseFirestore
            .collection(FirestoreContract.Rooms.COLLECTION_KEY)
            .document(uid)
            .delete()
            .await()
    }

    private fun MeetingRoomFormRemoteModel.toDocumentData(): Map<String, Any> =
        hashMapOf(
            FirestoreContract.Rooms.NAME_KEY to name,
            FirestoreContract.Rooms.ADDRESS_KEY to address,
        )
}