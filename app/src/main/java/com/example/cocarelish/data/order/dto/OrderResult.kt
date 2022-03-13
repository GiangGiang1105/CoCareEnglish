package com.example.cocarelish.data.order.dto

data class OrderResult(
    val id: String = "",
    val essay_id: Int = -1,
    val detail_result_id : String = "",
    val order_id : String = "",
    val score: Int = 0,
    val uid: String = ""
)
