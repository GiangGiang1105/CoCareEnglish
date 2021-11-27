package com.example.cocarelish.domain.auth.usecase

import com.example.cocarelish.data.authentication.remote.dto.RegisterRequest
import com.example.cocarelish.domain.auth.AuthRepository
import com.example.cocarelish.domain.auth.entity.RegisterEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository){

    fun execute(registerRequest: RegisterRequest) : Flow<Resource<RegisterEntity>> {
        return authRepository.register(registerRequest)
    }
}