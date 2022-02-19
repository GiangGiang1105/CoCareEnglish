package com.example.cocarelish.domain.auth

import com.example.cocarelish.base.BaseRepository
import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.data.authentication.remote.dto.RegisterRequest
import com.example.cocarelish.data.authentication.remote.dto.UserInfo
import com.example.cocarelish.domain.auth.entity.LoginEntity
import com.example.cocarelish.domain.auth.entity.RegisterEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow

abstract class AuthRepository : BaseRepository() {

    abstract fun login(loginRequest: LoginRequest) : Flow<Resource<LoginEntity>>

    abstract fun register(registerRequest: RegisterRequest) : Flow<Resource<RegisterEntity>>

    abstract suspend fun getUserInformation(user_id: String): Flow<Resource<UserInfo?>>

    abstract suspend fun setUpUserInformation(userInformation: UserInfo): Boolean
}