package com.example.cocarelish.domain.auth.usecase

import com.example.cocarelish.domain.auth.AuthRepository
import com.example.cocarelish.domain.auth.entity.UserEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase  @Inject constructor(private val authRepository: AuthRepository){

    fun execute(user_id: Int) : Flow<Resource<UserEntity>> {
        return authRepository.getUserInformation(user_id)
    }
}