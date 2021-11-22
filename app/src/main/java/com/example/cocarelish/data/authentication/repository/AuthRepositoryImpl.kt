package com.example.cocarelish.data.authentication.repository

import android.util.Log
import com.example.cocarelish.data.authentication.remote.api.AuthApi
import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.domain.auth.AuthRepository
import com.example.cocarelish.domain.auth.entity.LoginEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authApi: AuthApi): AuthRepository() {
    override fun login(loginRequest: LoginRequest): Flow<Resource<LoginEntity>> {
        return flow {
            val response = safeApiCall { authApi.login(loginRequest).data }
            Log.d("TAG", "login: $response")
            emit(response)
        }
    }
}