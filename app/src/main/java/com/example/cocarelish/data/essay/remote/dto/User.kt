package com.example.cocarelish.data.essay.remote.dto

data class User(
    val id: Int?,
    val name: String?,
    val email: String?,
    val role: String?,
    val address: String?,
    val gender: Int?,
    val phone_number: String?,
    val job: String?,
    val date_of_birth: String?,
    val rate_score: Int?
)