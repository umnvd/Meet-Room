@file:Suppress("UNCHECKED_CAST")

package com.umnvd.booking.data.services

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.umnvd.booking.data.models.MeetingEventDTO
import com.umnvd.booking.data.models.MeetingEventFormDTO
import com.umnvd.booking.data.models.MeetingRoomDTO
import com.umnvd.booking.data.models.UserDTO
import com.umnvd.booking.data.services.utils.FirestoreContract
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MeetingEventsService @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) {

    suspend fun getEvent(uid: String): MeetingEventDTO {
        val eventSnapshot = firebaseFirestore
            .collection(FirestoreContract.Events.COLLECTION_KEY)
            .document(uid)
            .get()
            .await()

        return getEventFromSnapshot(eventSnapshot)
    }

    suspend fun getEvents(): List<MeetingEventDTO> {
        val eventsSnapshots = firebaseFirestore
            .collection(FirestoreContract.Events.COLLECTION_KEY).get().await().documents

        return eventsSnapshots.map { getEventFromSnapshot(it) }
    }

    suspend fun createEvent(data: MeetingEventFormDTO): MeetingEventDTO {
        val documentData = buildDocumentData(data)

        val eventReference = firebaseFirestore
            .collection(FirestoreContract.Events.COLLECTION_KEY)
            .add(documentData)
            .await()

        return getEvent(eventReference.id)
    }

    suspend fun editEvent(uid: String, data: MeetingEventFormDTO): MeetingEventDTO {
        val documentData = buildDocumentData(data)

        firebaseFirestore
            .collection(FirestoreContract.Events.COLLECTION_KEY)
            .document(uid)
            .set(documentData)
            .await()

        return getEvent(uid)
    }

    suspend fun deleteEvent(uid: String) {
        firebaseFirestore
            .collection(FirestoreContract.Events.COLLECTION_KEY)
            .document(uid)
            .delete()
            .await()
    }

    private fun buildDocumentData(data: MeetingEventFormDTO): Map<String, Any> {
        val documentData = hashMapOf(
            FirestoreContract.Events.TITLE_KEY to data.title,
            FirestoreContract.Events.START_AT_KEY to data.startAt,
            FirestoreContract.Events.END_AT_KEY to data.endAt,
            FirestoreContract.Events.ROOM_KEY to firebaseFirestore
                .collection(FirestoreContract.Rooms.COLLECTION_KEY)
                .document(data.roomUid),
            FirestoreContract.Events.PARTICIPANTS_KEY to data.participantsUids.map {
                firebaseFirestore
                    .collection(FirestoreContract.Users.COLLECTION_KEY)
                    .document(it)
            }

        )
        data.description?.also { documentData[FirestoreContract.Events.DESCRIPTION_KEY] = it }
        return documentData
    }

    private suspend fun getEventFromSnapshot(eventSnapshot: DocumentSnapshot): MeetingEventDTO {
        val roomSnapshot = eventSnapshot
            .getDocumentReference(FirestoreContract.Rooms.COLLECTION_KEY)!!
            .get()
            .await()

        val roomDto = MeetingRoomDTO(
            uid = roomSnapshot.id,
            name = roomSnapshot.getString(FirestoreContract.Rooms.NAME_KEY)!!,
            address = roomSnapshot.getString(FirestoreContract.Rooms.ADDRESS_KEY)!!,
        )

        val participantsSnapshots =
            eventSnapshot.get(FirestoreContract.Events.PARTICIPANTS_KEY) as List<DocumentReference>

        val participantsDtos = participantsSnapshots.map {
            val userSnapshot = it.get().await()

            UserDTO(
                uid = userSnapshot.id,
                nickname = userSnapshot.getString(FirestoreContract.Users.NICKNAME_KEY)!!,
                fullName = userSnapshot.getString(FirestoreContract.Users.FULL_NAME_KEY)!!,
                photoUrl = userSnapshot.getString(FirestoreContract.Users.PHOTO_URL_KEY)!!,
            )
        }


        return MeetingEventDTO(
            uid = eventSnapshot.id,
            title = eventSnapshot.getString(FirestoreContract.Events.TITLE_KEY)!!,
            description = eventSnapshot.getString(FirestoreContract.Events.DESCRIPTION_KEY),
            startAt = eventSnapshot.getString(FirestoreContract.Events.START_AT_KEY)!!,
            endAt = eventSnapshot.getString(FirestoreContract.Events.END_AT_KEY)!!,
            room = roomDto,
            participants = participantsDtos,
        )
    }
}