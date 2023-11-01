package com.android.fetchcountriesapp.service

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


sealed class ErrorResponse(
    open val message: String
)

data class HttpError(
    @SerializedName("message") val errorMessage: String,
    @SerializedName("status") val errorCode: Int
)

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val value: T) : Result<T>()
    data class Failure(val errorHolder:ErrorResponse) : Result<Nothing>()}


/**
 * Wrapper class to encapsulate Response to be used across the app.
 */
sealed class ResultWrapper(override val message: String):Throwable(message){
    data class NetworkConnection(override val message: String) : ErrorResponse(message)
    data class BadRequest(override val message: String) : ErrorResponse(message)
    data class UnAuthorized(override val message: String) : ErrorResponse(message)
    data class InternalServerError(override val message: String) :ErrorResponse(message)
    data class ResourceNotFound(override val message: String) : ErrorResponse(message)
}


private fun asNetworkException(ex: Throwable): ErrorResponse {
    return when (ex) {
        is IOException -> {
            ResultWrapper.NetworkConnection(
                "No Internet Connection"
            )
        }
        is HttpException -> extractHttpExceptions(ex)
        else -> ResultWrapper.InternalServerError("Something went wrong...")
    }
}

private fun extractHttpExceptions(ex: HttpException): ErrorResponse {
    val body = ex.response()?.errorBody()
    val gson = GsonBuilder().create()
    val responseBody= gson.fromJson(body.toString(), JsonObject::class.java)
    val errorEntity = gson.fromJson(responseBody, HttpError::class.java)
    return when (errorEntity.errorCode) {
        401 ->
            ResultWrapper.BadRequest(errorEntity.errorMessage)

        500 ->
            ResultWrapper.InternalServerError(errorEntity.errorMessage)

        401 ->
            ResultWrapper.UnAuthorized(errorEntity.errorMessage)

        400 ->
            ResultWrapper.ResourceNotFound(errorEntity.errorMessage)

        else ->
            ResultWrapper.InternalServerError(errorEntity.errorMessage)

    }
}

suspend fun <T, R : Any> Call<T>.awaitResult(map: (T) -> R): Result<R> = suspendCancellableCoroutine { continuation ->
    try {
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, throwable: Throwable) {
                errorHappened(throwable)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    try {
                        continuation.resume(Result.Success(map(response.body()!!)), null)
                    } catch (throwable: Throwable) {
                        errorHappened(throwable)
                    }
                } else {
                    errorHappened(HttpException(response))
                }
            }

            private fun errorHappened(throwable: Throwable) {
                continuation.resume(Result.Failure(asNetworkException(throwable)), null)
            }
        })
    } catch (throwable: Throwable) {
        continuation.resume(Result.Failure(asNetworkException(throwable)), null)
    }

    continuation.invokeOnCancellation {
        cancel()
    }}