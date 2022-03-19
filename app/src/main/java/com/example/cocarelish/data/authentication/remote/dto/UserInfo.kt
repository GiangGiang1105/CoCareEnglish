package com.example.cocarelish.data.authentication.remote.dto

import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.DocumentSnapshot

data class UserInfo(
    var address: String = "",
    val created_at: Any =  "",
    val date_of_birth: Any= "",
    var email: String= "",
    var gender: Int = 0, //0 la nam la nu
    var id: String = "",
    val job: String="",
    var name: String= "",
    var phone_number: String = "",
    val rate_score: Int = 0,
    val role: Int = 0, //0 la sinh vien, 1 la giao vien
    val updated_at: String = ""
)  {
    companion object {
//        fun DocumentSnapshot.toUser(): UserInfo{
//            try{
//                val name = getString("name")
//                val phone_number = getString("phone_number")
//                val
//            }
//        }
    }
}