package com.example.cocarelish.data.model.user

data class User(
    var user_id: Int = 0,
    var account_id: Int = 0,
    var name: String,
    var email: String? = "",
    var role_id: Int = 0,
    var password: String,
    var andress: String? = "",
    var phone_number: String? = "",
    var job_id: Int = 0,
    var date_of_birth: Long? = 0
)
