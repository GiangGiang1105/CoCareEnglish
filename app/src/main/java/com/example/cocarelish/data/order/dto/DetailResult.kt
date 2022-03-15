package com.example.cocarelish.data.order.dto

data class DetailResult(
    val coherence: Int = 0,
    val lexical_score: Int = 4,
    val grammar_score: Int = 4,
    val vocabulary: Int = 4,
    val feedback: String = "",
    val general_comment: String = "",
    val id: String = "0"
)
