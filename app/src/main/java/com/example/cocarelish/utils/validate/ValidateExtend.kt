package com.example.cocarelish.utils.validate

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

object ValidateExtend {

    fun TextInputEditText.validate(validator: (String) -> Pair<Boolean,String>): Boolean {
        val data = validator(this.text.toString())
        this.error = if (data.first) null else data.second
        return data.first
    }
}

fun isValidEmail(text: String): Pair<Boolean,String> =
    Pair(text.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(text).matches(), "Nhập sai username")

fun isValidPassword(text: String):Pair<Boolean,String> = Pair(text.length > 6, "Nhập sai password")

