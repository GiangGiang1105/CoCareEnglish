package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.essay.remote.dto.Test
import com.example.cocarelish.domain.essay.usecase.TestUseCase
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.base.CommonCollapseEssayTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritingEssayViewModel @Inject constructor(
    private val testUseCase: TestUseCase,
    application: Application
) : CommonViewModel(application),
    CommonCollapseEssayTitle {
    override val isCollapse = MutableLiveData(true)
    private val _detailEssay: MutableLiveData<Test> = MutableLiveData()
    override val detailEssay: LiveData<Test>
        get() = _detailEssay
    val isRecording = MutableLiveData(false)
    val recordText = MutableLiveData("")

    fun imgRecordStateClick() {
        Log.d("TAGG", "imgRecordStateClick: ")
        isRecording.value = isRecording.value?.not()
    }

    fun getDetailTest(id_test: Int) {
        Log.d(TAG, "getDetailTest called with id test = $id_test")
        viewModelScope.launch {
            testUseCase.execute(id_test).onStart {
                showLoadingDialog(true)
            }.catch { exception ->
                showLoadingDialog(false)
                Log.d(TAG, "getDetailTest: error with exception $exception")
            }
                .collect { baseResult ->
                    showLoadingDialog(false)
                    Log.d(TAG, "getDetailTest: success with data = $baseResult")
                    when (baseResult) {
                        is Resource.Success -> {
                            _detailEssay.postValue(baseResult.value.tests[0])
                            Log.d(TAG, "getDetailTest: ${_detailEssay.value}")
                        }
                    }
                }
        }
    }

}