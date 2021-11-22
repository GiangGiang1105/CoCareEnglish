package com.example.cocarelish.data.common.utils

import com.example.cocarelish.utils.MyPreference
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(private val pref: MyPreference) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = pref.getToken()
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}