package com.example.cocarelish.data.order.dto

data class Order(
    val uid: String = "",
    val id: String = "",
    var content: String = "",
    val dead_line: Int = 2,
    val status_id: Int = -1,
    val time_post: Long = 0,
    val price: Long = 0,
    val essay_id: Int = 0
)