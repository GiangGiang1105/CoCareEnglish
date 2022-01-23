package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.essay.remote.dto.Deadline
import com.example.cocarelish.data.essay.remote.dto.SaveEssay
import com.example.cocarelish.data.essay.remote.dto.Test
import com.example.cocarelish.domain.essay.usecase.DeadlineUseCase
import com.example.cocarelish.domain.essay.usecase.SaveEssayUseCase
import com.example.cocarelish.domain.essay.usecase.TestUseCase
import com.example.cocarelish.utils.CoCareLishPrefence
import com.example.cocarelish.utils.Consts
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
    private val deadlineUseCase: DeadlineUseCase,
    private val saveEssayUseCase: SaveEssayUseCase,
    application: Application
) : CommonViewModel(application),
    CommonCollapseEssayTitle {

    private val coCareLishPrefence = CoCareLishPrefence(application.applicationContext)
    override val isCollapse = MutableLiveData(true)
    private val _detailEssay: MutableLiveData<Test> = MutableLiveData()
    override val detailEssay: LiveData<Test>
        get() = _detailEssay
    val isRecording = MutableLiveData(false)
    val recordText = MutableLiveData("")

    private val _levelName = MutableLiveData("")
    val levelName: LiveData<String>
        get() = _levelName
    private val _topicName = MutableLiveData("")
    val topicName: LiveData<String>
        get() = _topicName
    private var _idEssay = -1
    private var _idDeadline = -1

    /*private val _deadlineEssay = MutableLiveData(Consts.DEFAULT_DEADLINE)
    val deadlineEssay: LiveData<Int>
        get() = _deadlineEssay*/

    private val _listDeadlineEssay: MutableLiveData<List<Deadline>> = MutableLiveData()
    val listDeadlineEssay: LiveData<List<Deadline>>
        get() = _listDeadlineEssay

    private val _totalPriceEssay = MutableLiveData(Consts.DEFAULT_PRICE)
    val totalPriceEssay: LiveData<Double>
        get() = _totalPriceEssay

    private var content: String = ""

    fun setDeadlineEssay(deadline: Deadline) {
        _totalPriceEssay.postValue(Consts.DEFAULT_PRICE + deadline.deadline_price)
        _idDeadline = deadline.id
    }

    fun setLevelName(levelName: String) {
        _levelName.postValue(levelName)
    }

    fun setTopicName(topicName: String) {
        _topicName.postValue(topicName)
    }

    private fun setIdEssay(id_essay: Int) {
        _idEssay = id_essay
    }

    fun imgRecordStateClick() {
        Log.d("TAGG", "imgRecordStateClick: ")
        isRecording.value = isRecording.value?.not()
    }

    fun getDetailEssay(id_essay: Int) {
        Log.d(TAG, "getDetailTest called with id test = $id_essay")
        setIdEssay(id_essay)
        viewModelScope.launch {
            testUseCase.execute(id_essay).onStart {
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

    fun getAllDeadline() {
        Log.d(TAG, "getAllDeadline: ")
        viewModelScope.launch {
            deadlineUseCase.execute().onStart {
                Log.d(TAG, "getAllDeadline: onstart()")
            }.catch { exception ->
                Log.d(TAG, "getDetailTest: error with exception $exception")
            }.collect {
                Log.d(TAG, "getAllDeadline: with data = $it")
                when (it) {
                    is Resource.Success -> {
                        _listDeadlineEssay.postValue(it.value.deadlines)
                        Log.d(
                            TAG,
                            "getAllDeadline: post value success with ${_listDeadlineEssay.value}"
                        )
                    }
                }
            }
        }
    }

    fun navigationWritingEssayFragmentToPaymentFragment(content: String?) {
        content?.let {
            this.content = content
            navigate(R.id.action_writingEssayFragment_to_paymentFragment)
        }
    }

    fun userSaveWrittenEssay() {
        Log.d(TAG, "userSaveWrittenEssay: ")
        coCareLishPrefence.init()
        val idUser = coCareLishPrefence.getIdUser()
        val idType = coCareLishPrefence.getIdType()
        val saveEssay =
            totalPriceEssay.value?.let {
                SaveEssay(
                    user_id = idUser,
                    type_id = idType,
                    deadline_id = _idDeadline,
                    test_id = _idEssay,
                    total_price = it,
                    content = content
                )
            }
        Log.d(TAG, "userSaveWrittenEssay: saveEssay = $saveEssay")
        viewModelScope.launch {
            saveEssay?.let {
                saveEssayUseCase.execute(it).onStart {
                    Log.d(TAG, "userSaveWrittenEssay: onstart()")
                    showLoadingDialog(true)
                }.catch { exception ->
                    Log.d(TAG, "getDetailTest: error with exception $exception")
                    showLoadingDialog(false)
                }.collect {
                    showLoadingDialog(false)
                    when (it) {
                        is Resource.Success -> {
                            if (it.value.success) {
                                viewModelScope.launch {
                                    evenSender.send(CommonEvent.OnShowToast(it.value.data))
                                    evenSender.send(CommonEvent.OnBackScreen)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}