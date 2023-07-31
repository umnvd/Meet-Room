package com.umnvd.booking.data.utils

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.umnvd.booking.domain.EmailNotRegisteredException
import com.umnvd.booking.domain.NetworkException
import com.umnvd.booking.domain.PasswordInvalidException
import com.umnvd.booking.domain.UnauthorizedException
import com.umnvd.booking.domain.UnknownException

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
    } catch (e: FirebaseAuthInvalidUserException) {
        throw UnauthorizedException()
    } catch (e: Exception) {
        Log.e("FirebaseExceptionMapper", e.message ?: e.toString())
        throw UnknownException()
    }
}