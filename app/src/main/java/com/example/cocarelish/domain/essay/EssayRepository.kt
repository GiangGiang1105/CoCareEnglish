package com.example.cocarelish.domain.essay

import com.example.cocarelish.base.BaseRepository
import com.example.cocarelish.base.BaseResponse
import com.example.cocarelish.data.essay.remote.dto.*
import com.example.cocarelish.domain.essay.entity.*
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow

abstract class EssayRepository : BaseRepository() {

    abstract fun getAllLevels(): Flow<Resource<List<Level>>>

    abstract fun getAllTopics(): Flow<Resource<List<Topic>>>

    abstract fun getAllTopicsById(topic_id: Int): Flow<Resource<TopicEntity>>

    abstract fun getTestById(test_id: Int): Flow<Resource<Test>>

    abstract fun getAllEssayOfUser(essay_id: String): Flow<Resource<EssayOfUserEntity>>

    abstract fun getAllQuestionByTopicId(id: Int): Flow<Resource<List<Test>>>

    abstract fun getAllDeadline(): Flow<Resource<DeadlineEntity>>

    abstract fun userSaveWrittenEssay(saveEssay: SaveEssay): Flow<Resource<BaseResponse<String>>>
}