package com.umnvd.booking.data.services.utils

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.umnvd.booking.domain.errors.EmailNotRegisteredException
import com.umnvd.booking.domain.errors.NetworkException
import com.umnvd.booking.domain.errors.PasswordInvalidException

suspend fun <T> withFirebaseExceptionMapper(
    block: suspend () -> T
): T {
    try {
        return block()
    } catch (e: FirebaseNetworkException) {
        throw NetworkException()
    } catch (e: FirebaseAuthInvalidUserException) {
        throw EmailNotRegisteredException()
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        throw PasswordInvalidException()
    } catch (e: FirebaseFirestoreException) {
        Log.d("FirebaseExceptionMapper", "${e.code.name} : ${e.message ?: e.toString()}")
        throw e
    } catch (e: Exception) {
        Log.d("FirebaseExceptionMapper", e.message ?: e.toString())
        throw e
    }
}