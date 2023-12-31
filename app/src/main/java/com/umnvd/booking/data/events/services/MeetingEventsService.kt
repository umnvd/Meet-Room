package com.umnvd.booking.data.events.services

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.umnvd.booking.data.events.models.MeetingEventFormRemoteModel
import com.umnvd.booking.data.events.models.MeetingEventRemoteModel
import com.umnvd.booking.data.utils.FirestoreContract
import com.umnvd.booking.data.utils.toRoomRemote
import com.umnvd.booking.data.utils.toUserRemote
import com.umnvd.booking.data.utils.withFBExceptionMapper
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MeetingEventsService @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) {

    suspend fun getEvent(uid: String): MeetingEventRemoteModel = withFBExceptionMapper {
        return@withFBExceptionMapper firebaseFirestore
            .collection(FirestoreContract.Events.COLLECTION_KEY)
            .document(uid)
            .get()
            .await()
            .toEventRemote()!!
    }

    suspend fun getEvents(): List<MeetingEventRemoteModel> = withFBExceptionMapper {
        return@withFBExceptionMapper firebaseFirestore
            .collection(FirestoreContract.Events.COLLECTION_KEY)
            .get()
            .await()
            .documents.mapNotNull { it.toEventRemote() }
    }

    suspend fun createEvent(data: MeetingEventFormRemoteModel): MeetingEventRemoteModel =
        withFBExceptionMapper {
            val eventReference = firebaseFirestore
                .collection(FirestoreContract.Events.COLLECTION_KEY)
                .add(data.toDocumentData())
                .await()
            return@withFBExceptionMapper getEvent(eventReference.id)
        }

    suspend fun editEvent(uid: String, data: MeetingEventFormRemoteModel): MeetingEventRemoteModel =
        withFBExceptionMapper {
            firebaseFirestore
                .collection(FirestoreContract.Events.COLLECTION_KEY)
                .document(uid)
                .update(data.toDocumentData())
                .await()

            return@withFBExceptionMapper getEvent(uid)
        }

    suspend fun deleteEvent(uid: String): Unit = withFBExceptionMapper {
        firebaseFirestore
            .collection(FirestoreContract.Events.COLLECTION_KEY)
            .document(uid)
            .delete()
            .await()
    }

    private fun MeetingEventFormRemoteModel.toDocumentData(): HashMap<String, Any?> =
        hashMapOf(
            FirestoreContract.Events.TITLE_KEY to title,
            FirestoreContract.Events.DESCRIPTION_KEY to description,
            FirestoreContract.Events.START_AT_KEY to startAt,
            FirestoreContract.Events.END_AT_KEY to endAt,
            FirestoreContract.Events.ROOM_KEY to firebaseFirestore
                .collection(FirestoreContract.Rooms.COLLECTION_KEY)
                .document(roomUid),
            FirestoreContract.Events.PARTICIPANTS_KEY to participantsUids.map {
                firebaseFirestore
                    .collection(FirestoreContract.Users.COLLECTION_KEY)
                    .document(it)
            }
        )

    @Suppress("UNCHECKED_CAST")
    private suspend fun DocumentSnapshot.toEventRemote(): MeetingEventRemoteModel? =
        withFBExceptionMapper {
            try {
                val roomSnapshot = getDocumentReference(FirestoreContract.Events.ROOM_KEY)!!
                    .get()
                    .await()

                val participantsSnapshots =
                    get(FirestoreContract.Events.PARTICIPANTS_KEY) as List<DocumentReference>

                val participants = participantsSnapshots.map {
                    it.get().await().toUserRemote()
                }

                return@withFBExceptionMapper MeetingEventRemoteModel(
                    uid = id,
                    title = getString(FirestoreContract.Events.TITLE_KEY)!!,
                    description = getString(FirestoreContract.Events.DESCRIPTION_KEY)!!,
                    startAt = getString(FirestoreContract.Events.START_AT_KEY)!!,
                    endAt = getString(FirestoreContract.Events.END_AT_KEY)!!,
                    room = roomSnapshot.toRoomRemote(),
                    participants = participants,
                )

            } catch (e: Exception) {
                return@withFBExceptionMapper null
            }
        }
}