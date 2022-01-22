package com.example.cocarelish.base

import android.util.Log
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
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
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}