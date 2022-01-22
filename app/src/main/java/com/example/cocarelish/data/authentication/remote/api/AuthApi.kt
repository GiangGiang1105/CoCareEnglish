package com.example.cocarelish.data.authentication.remote.api

import com.example.cocarelish.base.BaseResponse
import com.example.cocarelish.data.authentication.remote.dto.*
import com.example.cocarelish.domain.auth.entity.LoginEntity
import com.example.cocarelish.domain.auth.entity.RegisterEntity
import com.example.cocarelish.domain.auth.entity.UserEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): BaseResponse<LoginEntity>

    @POST("user")
    suspend fun register(@Body registerRequest: RegisterRequest): BaseResponse<RegisterEntity>

    @GET("user/{user_id}")
    suspend fun getUserInformation(@Path("user_id") user_id: Int): BaseResponse<UserEntity>
}