package com.example.cocarelish.domain.auth.entity

data class LoginEntity(
    val token: String?,
    val user_info: UserInfo
)