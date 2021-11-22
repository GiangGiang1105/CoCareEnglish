package com.example.cocarelish.domain.auth.usecase

import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.domain.auth.AuthRepository
import com.example.cocarelish.domain.auth.entity.LoginEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticatioUsecase @Inject constructor (private val authRepository: AuthRepository) {
    fun execute(loginRequest: LoginRequest): Flow<Resource<LoginEntity>>{
        return authRepository.login(loginRequest)
    }
}