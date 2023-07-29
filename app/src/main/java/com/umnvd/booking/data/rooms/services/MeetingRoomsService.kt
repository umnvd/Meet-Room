package com.umnvd.booking.data.rooms.services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.umnvd.booking.data.rooms.models.MeetingRoomFormRemoteModel
import com.umnvd.booking.data.rooms.models.MeetingRoomRemoteModel
import com.umnvd.booking.data.utils.FirestoreContract
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MeetingRoomsService @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) {

    suspend fun getRoom(uid: String): MeetingRoomRemoteModel {
        val roomSnapshot = firebaseFirestore
            .collection(FirestoreContract.Rooms.COLLECTION_KEY)
            .document(uid)
            .get()
            .await()

        return MeetingRoomRemoteModel(
            uid = roomSnapshot.id,
            name = roomSnapshot.getString(FirestoreContract.Rooms.NAME_KEY)!!,
            address = roomSnapshot.getString(FirestoreContract.Rooms.ADDRESS_KEY)!!,
        )
    }

    suspend fun getRooms(): List<MeetingRoomRemoteModel> {
        val snapshots = firebaseFirestore
            .collection(FirestoreContract.Rooms.COLLECTION_KEY)
            .get()
            .await()
            .documents

        Log.d("ABOBA", snapshots.toString())

        return snapshots.map {
            MeetingRoomRemoteModel(
                uid = it.id,
                name = it.getString(FirestoreContract.Rooms.NAME_KEY)!!,
                address = it.getString(FirestoreContract.Rooms.ADDRESS_KEY)!!,
            )
        }
    }

    suspend fun createRoom(data: MeetingRoomFormRemoteModel): MeetingRoomRemoteModel {
        val documentData = hashMapOf(
            FirestoreContract.Rooms.NAME_KEY to data.name,
            FirestoreContract.Rooms.ADDRESS_KEY to data.address,
        )

        val roomReference = firebaseFirestore
            .collection(FirestoreContract.Rooms.COLLECTION_KEY)
            .add(documentData)
            .await()

        return getRoom(roomReference.id)
    }

    suspend fun editRoom(uid: String, data: MeetingRoomFormRemoteModel): MeetingRoomRemoteModel {
        val documentData = hashMapOf(
            FirestoreContract.Rooms.NAME_KEY to data.name,
            FirestoreContract.Rooms.ADDRESS_KEY to data.address,
        )

        firebaseFirestore
            .collection(FirestoreContract.Rooms.COLLECTION_KEY)
            .document(uid)
            .set(documentData)
            .await()

        return getRoom(uid)
    }

    suspend fun deleteRoom(uid: String) {
        firebaseFirestore
            .collection(FirestoreContract.Rooms.COLLECTION_KEY)
            .document(uid)
            .delete()
            .await()
    }
}