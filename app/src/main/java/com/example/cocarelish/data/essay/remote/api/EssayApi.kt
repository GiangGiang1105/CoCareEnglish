package com.example.cocarelish.data.essay.remote.api

import com.example.cocarelish.base.BaseResponse
import com.example.cocarelish.data.essay.remote.dto.SaveEssay
import com.example.cocarelish.data.essay.remote.dto.Type
import com.example.cocarelish.domain.essay.entity.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EssayApi {

    @GET("level")
    suspend fun getAllLevels(): BaseResponse<LevelList>

//    @GET("type")
//    suspend fun getAllTypes(): BaseResponse<List<Type>>

    @GET("topic/{topic_id}")
    suspend fun getAllTopicsById(@Path("topic_id") topic_id: Int): BaseResponse<TopicEntity>

    @GET("test/{test_id}")
    suspend fun getDetailTest(@Path("test_id") test_id: Int): BaseResponse<TestEntity>

    @GET("my-essay/{id_user}")
    suspend fun getAllEssayOfUser(@Path("id_user") id_user: String): BaseResponse<EssayOfUserEntity>

    //TODO: Chưa có API
    /*@GET("essay")
    suspend fun getAllEssays():*/

    @GET("test-by-topic/{id}")
    suspend fun getAllTestByTopic(@Path("id") id: Int): BaseResponse<TestByTopicEntity>

    @GET("deadline")
    suspend fun getAllDeadline(): BaseResponse<DeadlineEntity>

    @POST("order")
    suspend fun userSaveWrittenEssay(@Body saveEssay: SaveEssay): BaseResponse<String>
}