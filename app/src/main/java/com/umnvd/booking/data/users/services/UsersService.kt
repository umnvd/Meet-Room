package com.umnvd.booking.data.users.services

import com.google.firebase.firestore.FirebaseFirestore
import com.umnvd.booking.data.users.models.UserRemoteModel
import com.umnvd.booking.data.utils.FirestoreContract
import com.umnvd.booking.data.utils.toUserRemote
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersService @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) {

    suspend fun getUser(uid: String): UserRemoteModel = firebaseFirestore
        .collection(FirestoreContract.Users.COLLECTION_KEY)
        .document(uid)
        .get()
        .await()
        .toUserRemote()

    suspend fun getUsers(): List<UserRemoteModel> = firebaseFirestore
        .collection(FirestoreContract.Users.COLLECTION_KEY)
        .get()
        .await()
        .documents
        .map { it.toUserRemote() }
}