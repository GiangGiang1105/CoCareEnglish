package com.example.cocarelish.data.authentication.remote.api

import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.data.authentication.remote.dto.LoginResponse
import com.example.cocarelish.data.authentication.remote.dto.RegisterRequest
import com.example.cocarelish.data.authentication.remote.dto.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("user")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse
}