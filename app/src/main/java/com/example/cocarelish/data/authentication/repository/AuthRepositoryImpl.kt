package com.example.cocarelish.data.authentication.repository

import android.util.Log
import com.example.cocarelish.data.authentication.remote.api.AuthApi
import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.data.authentication.remote.dto.RegisterRequest
import com.example.cocarelish.domain.auth.AuthRepository
import com.example.cocarelish.domain.auth.entity.LoginEntity
import com.example.cocarelish.domain.auth.entity.RegisterEntity
import com.example.cocarelish.domain.auth.entity.UserEntity
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

    override fun register(registerRequest: RegisterRequest): Flow<Resource<RegisterEntity>> {
        return flow {
            val response = safeApiCall { authApi.register(registerRequest).data
            }
            Log.d("TAG", "register: $response")
            emit(response)
        }
    }

    override fun getUserInformation(user_id: Int): Flow<Resource<UserEntity>> {
        return flow {
            val response = safeApiCall { authApi.getUserInformation(user_id).data }
            Log.d("TAG", "getUserInformation: $response ")
            emit(response)
        }
    }
}