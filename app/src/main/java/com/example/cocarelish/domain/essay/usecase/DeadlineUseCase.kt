package com.example.cocarelish.domain.essay.usecase

import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.DeadlineEntity
import com.example.cocarelish.domain.essay.entity.EssayOfUserEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeadlineUseCase @Inject constructor(private val essayRepository: EssayRepository) {

    fun execute(): Flow<Resource<DeadlineEntity>> {
        return essayRepository.getAllDeadline()
    }
}