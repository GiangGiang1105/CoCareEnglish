package com.example.cocarelish.data.network

import com.example.cocarelish.BuildConfig
import com.example.cocarelish.utils.Consts.BASE_URL
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    fun <Api> buildApi(
        api: Class<Api>,
        authToken: String? = null
    ): Api {
//        val authenticator = TokenAuthenticator(context, buildTokenApi())
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor{
                    chain -> chain.proceed(chain.request().newBuilder().also {
                it.addHeader("Authorization","Bearer $authToken")
            }.build())
            }.also { client ->
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                client.addInterceptor(logging)
            }.build())//hiện log
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }


    private fun getRetrofitClient(authenticator: Authenticator? = null): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())
            }.also { client ->
                authenticator?.let { client.authenticator(it) }
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }
    companion object{

    }
}
