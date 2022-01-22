package com.example.cocarelish.domain.essay.usecase

import com.example.cocarelish.base.BaseResponse
import com.example.cocarelish.data.essay.remote.dto.SaveEssay
import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.MessageEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveEssayUseCase @Inject constructor(private val essayRepository: EssayRepository) {

    fun execute(saveEssay: SaveEssay): Flow<Resource<BaseResponse<String>>> {
        return essayRepository.userSaveWrittenEssay(saveEssay)
    }
}