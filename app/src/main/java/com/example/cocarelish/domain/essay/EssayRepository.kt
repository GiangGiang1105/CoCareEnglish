package com.example.cocarelish.domain.essay

import com.example.cocarelish.base.BaseRepository
import com.example.cocarelish.base.BaseResponse
import com.example.cocarelish.data.essay.remote.dto.SaveEssay
import com.example.cocarelish.domain.essay.entity.*
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow

abstract class EssayRepository : BaseRepository() {

    abstract fun getAllLevels(): Flow<Resource<LevelEntity>>

    abstract fun getAllTypes(): Flow<Resource<TypeEntity>>

    abstract fun getAllTopicsById(topic_id: Int): Flow<Resource<TopicEntity>>

    abstract fun getAllTestById(test_id: Int): Flow<Resource<TestEntity>>

    abstract fun getAllEssayOfUser(essay_id: String): Flow<Resource<EssayOfUserEntity>>

    abstract fun getAllTestByTopic(id: Int): Flow<Resource<TestByTopicEntity>>

    abstract fun getAllDeadline(): Flow<Resource<DeadlineEntity>>

    abstract fun userSaveWrittenEssay(saveEssay: SaveEssay): Flow<Resource<BaseResponse<String>>>
}