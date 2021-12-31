package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.domain.essay.usecase.EssayOfUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyEssayViewModel @Inject constructor(
    private val essayOfUserUseCase: EssayOfUserUseCase,
    application: Application
) : CommonViewModel(application) {



}