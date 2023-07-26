package com.umnvd.booking.data.services

import com.google.firebase.firestore.FirebaseFirestore
import com.umnvd.booking.data.models.UserDTO
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersService @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) {

    suspend fun getUser(uid: String): UserDTO {
        val userSnapshot = firebaseFirestore
            .collection(FirestoreContract.Users.COLLECTION_KEY)
            .document(uid)
            .get()
            .await()

        return UserDTO(
            uid = userSnapshot.id,
            nickname = userSnapshot.getString(FirestoreContract.Users.NICKNAME_KEY)!!,
            fullName = userSnapshot.getString(FirestoreContract.Users.FULL_NAME_KEY)!!,
            photoUrl = userSnapshot.getString(FirestoreContract.Users.PHOTO_URL_KEY)!!,
        )
    }
}