package com.example.cocarelish.data.essay.remote.dto

data class EssayOfUser(
    val id: Int,
    val user_id: Int,
    val status: Int,
    val type_name: String,
    val question_of_test: String,
    val teacher_name: String,
    val content: String,
    val score: String,
    val updated_at: String
)

