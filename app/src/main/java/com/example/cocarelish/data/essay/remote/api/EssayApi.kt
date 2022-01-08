package com.example.cocarelish.data.essay.remote.api

import com.example.cocarelish.base.BaseResponse
import com.example.cocarelish.domain.essay.entity.*
import retrofit2.http.GET
import retrofit2.http.Path

interface EssayApi {

    @GET("level")
    suspend fun getAllLevels(): BaseResponse<LevelEntity>

    @GET("type")
    suspend fun getAllTypes(): BaseResponse<TypeEntity>

    @GET("topic/{topic_id}")
    suspend fun getAllTopicsById(@Path("topic_id") topic_id: Int): BaseResponse<TopicEntity>

    @GET("test/{test_id}")
    suspend fun getDetailTest(@Path("test_id") test_id: Int): BaseResponse<TestEntity>

    @GET("my-essay/{id_user}")
    suspend fun getAllEssayOfUser(@Path("id_user") id_user: Int): BaseResponse<EssayOfUserEntity>

    //TODO: Chưa có API
    /*@GET("essay")
    suspend fun getAllEssays():*/

    @GET("test-by-topic/{id}")
    suspend fun getAllTestByTopic(@Path("id") id: Int): BaseResponse<TestByTopicEntity>


}