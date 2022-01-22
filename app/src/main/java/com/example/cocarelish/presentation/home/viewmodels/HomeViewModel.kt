package com.example.cocarelish.presentation.home.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.domain.room.entity.Mission
import com.example.cocarelish.domain.room.entity.MissionType
import com.example.cocarelish.domain.room.usecase.MissionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
  private val  missionUseCase: MissionUseCase
) : CommonViewModel(application) {



    lateinit var listMission : LiveData<List<Mission>>

    suspend fun getAllMission() = missionUseCase.getAllMission()

}