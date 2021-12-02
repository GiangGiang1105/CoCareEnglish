package com.example.cocarelish.domain.essay.usecase

import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.TestEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TestUseCase  @Inject constructor(private val essayRepository: EssayRepository){

    fun execute(test_id: Int): Flow<Resource<TestEntity>> {
        return essayRepository.getAllTestById(test_id)
    }
}