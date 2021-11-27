package com.example.cocarelish.data.authentication.remote.dto

import com.example.cocarelish.domain.auth.entity.RegisterEntity

data class RegisterResponse(
    val `data`: RegisterEntity,
    val success: Boolean
)
