package com.example.cocarelish.data.authentication.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("is_remember") val isRemember: Boolean = true
)