package com.example.cocarelish.data.order.dto

import java.util.*

data class DetailResult(
    val coherence: Int = 0,
    val lexical_score: Int = 4,
    val grammar_score: Int = 4,
    val vocabulary: Int = 4,
    val feedback: String = "",
    val general_comment: String = "",
    val id: String = "0",
//    val review: List<Map<String, Objects>> = listOf()
)
