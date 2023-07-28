package com.umnvd.booking.data.users.services

import com.google.firebase.firestore.FirebaseFirestore
import com.umnvd.booking.data.users.models.UserRemoteModel
import com.umnvd.booking.data.utils.FirestoreContract
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersService @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) {

    suspend fun getUser(uid: String): UserRemoteModel {
        val userSnapshot = firebaseFirestore
            .collection(FirestoreContract.Users.COLLECTION_KEY)
            .document(uid)
            .get()
            .await()

        return UserRemoteModel(
            uid = userSnapshot.id,
            nickname = userSnapshot.getString(FirestoreContract.Users.NICKNAME_KEY)!!,
            fullName = userSnapshot.getString(FirestoreContract.Users.FULL_NAME_KEY)!!,
            photoUrl = userSnapshot.getString(FirestoreContract.Users.PHOTO_URL_KEY)!!,
        )
    }

    suspend fun getUsers(): List<UserRemoteModel> {
        val usersSnapshots = firebaseFirestore
            .collection(FirestoreContract.Users.COLLECTION_KEY)
            .get()
            .await()
            .documents

        return usersSnapshots.map {
            UserRemoteModel(
                uid = it.id,
                nickname = it.getString(FirestoreContract.Users.NICKNAME_KEY)!!,
                fullName = it.getString(FirestoreContract.Users.FULL_NAME_KEY)!!,
                photoUrl = it.getString(FirestoreContract.Users.PHOTO_URL_KEY)!!,
            )
        }
    }
}