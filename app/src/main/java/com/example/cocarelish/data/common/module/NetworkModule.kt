package com.example.cocarelish.data.common.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cocarelish.data.common.room.AppDataBase
import com.example.cocarelish.data.common.utils.RequestInterceptor
import com.example.cocarelish.domain.room.entity.Mission
import com.example.cocarelish.utils.Consts
import com.example.cocarelish.utils.MyPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule() {

    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl(Consts.BASE_URL)
        }.build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(requestInterceptor: RequestInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(5, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor)
        }.build()
    }

    @Provides
    fun provideRequestInterceptor(prefs: MyPreference): RequestInterceptor {
        return RequestInterceptor(prefs)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {

        return Room.databaseBuilder(
            context, AppDataBase::class.java,
            AppDataBase::class.java.simpleName
        ).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        provideAppDatabase(context).missionDAO()
                            .insertAllMission(Mission.listMissionInit)
                    }
                }
            }
        ).build()

    }
}