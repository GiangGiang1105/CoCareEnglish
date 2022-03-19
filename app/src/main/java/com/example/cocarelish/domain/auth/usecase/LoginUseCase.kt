package com.example.cocarelish.domain.auth.usecase

import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.data.authentication.remote.dto.RegisterRequest
import com.example.cocarelish.data.authentication.remote.dto.UserInfo
import com.example.cocarelish.domain.auth.AuthRepository
import com.example.cocarelish.domain.auth.entity.LoginEntity
import com.example.cocarelish.domain.auth.entity.RegisterEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    fun execute(loginRequest: LoginRequest): Flow<Resource<LoginEntity>> {
        return authRepository.login(loginRequest)
    }

    suspend fun getUserInformation(userID: String): Flow<Resource<UserInfo?>> {
        return authRepository.getUserInformation(userID)
    }

    suspend fun setUserInformation(data: UserInfo): Flow<Boolean>{
        return authRepository.setUpUserInformation(data)
    }

}