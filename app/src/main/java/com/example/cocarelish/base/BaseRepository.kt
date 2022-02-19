package com.example.cocarelish.base

import android.util.Log
import com.example.cocarelish.utils.Resource
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.math.log

abstract class BaseRepository {
    companion object{
        const val TAG = "safeCall"
    }
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("BaseRepository", "safeApiCall:${apiCall.invoke()} ")
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Log.d("BaseRepository ", "safeApiCall: ${throwable.message}")
                when (throwable) {
                    is HttpException -> {
                        Log.d("BaseRepository ", "safeApiCall:${ throwable.response()?.errorBody()} ")
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody().toString())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }

    suspend inline fun <T> safeCall(message: String = "" ,crossinline action: () -> Resource<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                action()
            } catch (e: FirebaseFirestoreException) {

                when (e.code) {
                    FirebaseFirestoreException.Code.NOT_FOUND -> {
                        Log.d(TAG, "safeCall -- failure: $message -- ${e.message}")
                        Resource.Failure(false,e.code.value(), e.localizedMessage)
                    }
                    else -> Resource.Failure(false,e.code.value(),e.localizedMessage)
                }
            } catch (e: Exception) {
                Log.d(TAG, "safeCall -- failure: $message -- ${e.message}")
                Resource.Failure(true, -1, e.localizedMessage)
            }
        }
    }
}