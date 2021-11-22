package com.example.cocarelish.domain.auth

import com.example.cocarelish.base.BaseRepository
import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.domain.auth.entity.LoginEntity
import com.example.cocarelish.utils.Resource
import dagger.Binds
import kotlinx.coroutines.flow.Flow

abstract class AuthRepository : BaseRepository() {
    abstract fun login(loginRequest: LoginRequest) : Flow<Resource<LoginEntity>>
}