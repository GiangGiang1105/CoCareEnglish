package com.example.cocarelish.domain.auth.entity

import com.example.cocarelish.data.authentication.remote.dto.UserInfo

data class LoginEntity(
    val token: String?,
    val user_info: UserInfo
)