package com.example.cocarelish.utils

import android.content.Context
import android.content.SharedPreferences

class CoCareLishPrefence(private val context: Context) {

    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPref: SharedPreferences

    fun init() {
        sharedPref = context.getSharedPreferences(NAME_PREFRENCE, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun putIdUser(id_user: Int) {
        editor.apply {
            putInt(ID_USER, id_user)
            commit()
        }
    }

    fun getIdUser(): Int = sharedPref.getInt(ID_USER, -1)

    fun putIdType(id_type: Int) {
        editor.apply {
            putInt(ID_TYPE, id_type)
            commit()
        }
    }

    fun getIdType(): Int = sharedPref.getInt(ID_TYPE, -1)

    companion object {
        const val NAME_PREFRENCE = "CoCareLishPrefence"
        const val ID_USER = "Id_User"
        const val ID_TYPE = "Id_Type"
    }
}