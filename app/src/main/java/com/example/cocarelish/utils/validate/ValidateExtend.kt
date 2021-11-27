package com.example.cocarelish.utils.validate

import android.util.Log
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

object ValidateExtend {

    fun TextInputEditText.validate(message: String, validator: (String) -> Boolean): Boolean {
        val isValid = validator(this.text.toString())
        this.error = if (isValid) null else message
        return isValid
    }

    fun TextInputEditText.validateSamePassword(
        message: String,
        mainPassword: String
    ): Boolean {
        val isValid = mainPassword == this.text.toString()
        this.error = if (isValid) null else message
        return isValid
    }
}

fun isValidateName(text: String): Boolean = text.isNotEmpty() && text.length > 8 && text.length < 50

fun isValidEmail(text: String): Boolean {
    Log.d("TAGG", "isValidEmail: text")
    return text.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(text).matches()
}

fun isValidPassword(text: String): Boolean = text.length > 6


