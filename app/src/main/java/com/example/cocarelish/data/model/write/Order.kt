package com.example.cocarelish.data.model.write

data class Order(
    var order_id: Int = 0,
    var student_id: Int? =null,
    var teacher_id: Int? = null,
    var status_id: Int? = null,
    var sent_date: Long,
    var received_date: Long,
    var essay_id: Int = 0,
    var level_id: Int = 0,
    var type_id: Int = 0,
    var total_price: Double = 0.0,
    var deadline_id: Int = 0
)