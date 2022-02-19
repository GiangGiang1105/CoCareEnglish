package com.example.cocarelish.data.essay.remote.dto

data class SaveEssay(
    val user_id: String,
    val test_id: Int,
    val deadline_id: Int,
    val type_id: Int,
    val total_price: Double,
    val content: String
)
