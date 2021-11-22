package com.example.cocarelish.data.authentication.remote.dto

import com.example.cocarelish.domain.auth.entity.LoginEntity

data class LoginResponse(
    val `data`: LoginEntity,
    val success: Boolean
)