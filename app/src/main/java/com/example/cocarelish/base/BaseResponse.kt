package com.example.cocarelish.base

data class BaseResponse<T>(
    val `data` : T,
    val success: Boolean
)