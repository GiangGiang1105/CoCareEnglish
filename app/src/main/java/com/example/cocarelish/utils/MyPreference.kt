package com.example.cocarelish.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


//Reference: https://stackoverflow.com/questions/63643065/the-best-way-to-wrap-sharedpreference-object-in-hilt
// Create SharePreference with hilts
@Singleton
class MyPreference @Inject constructor(@ApplicationContext context: Context) {
    companion object{
        const val TAG = "MyPreference"
        private const val SHARE_PREFERENCE_NAME = "share_preference_name"

        private const val USER_ID = "user_id"
        private const val IMAGE_LINK = "image_link"
        private const val NAVIGATION_MY_ESSAY = "navigation_my_essay"
    }
    private val sharedPref: SharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun saveImageLink(imagePath: String){
        Log.d(TAG, "saveImageLink: $imagePath ")
        put(IMAGE_LINK, imagePath)
    }

    fun getImageLink(): String{
        Log.d(TAG, "getImageLink:${get(IMAGE_LINK, String::class.java)} ")
        return get(IMAGE_LINK, String::class.java)
    }

    fun getUserID() : String {
        return get(USER_ID, String::class.java)
    }

    fun saveUserID(token: String){
        put(USER_ID, token)
    }

    fun saveNavigationToMyEssay(isNavigation: Boolean){
        put(NAVIGATION_MY_ESSAY, isNavigation)
    }

    fun getNavigationToMyEssay(): Boolean{
        return get(NAVIGATION_MY_ESSAY, Boolean::class.java)
    }

    private fun <T> put(key: String, data: T){
        val editor = sharedPref.edit()

        when(data){
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    fun clear() {
        sharedPref.edit().run {
            remove(USER_ID)
        }.apply()
    }
}