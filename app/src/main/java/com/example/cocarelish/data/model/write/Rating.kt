package com.example.cocarelish.data.model.write

data class Rating(
    var rating_id: Int = 0,
    var student_id: Int = 0,
    var teacher_id: Int = 0,
    var order_id: Int = 0,
    var stars: Int = 0,
    var comment: String = ""
)
