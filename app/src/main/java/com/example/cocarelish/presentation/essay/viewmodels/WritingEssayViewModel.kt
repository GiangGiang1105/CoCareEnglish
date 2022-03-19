package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.base.Event
import com.example.cocarelish.data.essay.remote.dto.Deadline
import com.example.cocarelish.data.essay.remote.dto.Test
import com.example.cocarelish.data.order.dto.Order
import com.example.cocarelish.domain.essay.usecase.EssayOfSystemUseCase
import com.example.cocarelish.domain.order.OrderRepository
import com.example.cocarelish.presentation.grade.viewmodel.combine
import com.example.cocarelish.utils.Consts
import com.example.cocarelish.utils.MyPreference
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.Title.IELTS_WRITING_TASK_1
import com.example.cocarelish.utils.Title.IELTS_WRITING_TASK_2
import com.example.cocarelish.utils.base.CommonCollapseEssayTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WritingEssayViewModel @Inject constructor(
    private val orderUseCase: OrderRepository,
    private val essayOfSystemUseCase: EssayOfSystemUseCase,
    private val myPreference: MyPreference,
    application: Application
) : CommonViewModel(application),
    CommonCollapseEssayTitle {

    init {
        getAllDeadline()
    }

    override val isCollapse = MutableLiveData(true)
    private val _detailEssay: MutableLiveData<Test> = MutableLiveData()

    override val detailEssay: LiveData<Test>
        get() = _detailEssay


    val isRecording = MutableLiveData(false)
    val recordText = MutableLiveData(Event(""))

    private val _levelName = MutableLiveData("")
    val levelName: LiveData<String>
        get() = _levelName
    private val _topicName = MutableLiveData("")
    val topicName: LiveData<String>
        get() = _topicName
    private var _idEssay = -1
    private var _idDeadline = -1
    var firstPrice = _levelName.map {
        if(it == IELTS_WRITING_TASK_1 || it == IELTS_WRITING_TASK_2){
            20000
        }else
            10000
    }

    var secondPrice = MutableLiveData<Long>(10000)

    var secondName = MutableLiveData("8 hours")

    private var speakingTest = MutableLiveData("")

    private val _listDeadlineEssay: MutableLiveData<List<Deadline>> = MutableLiveData()
    val listDeadlineEssay: LiveData<List<Deadline>>
        get() = _listDeadlineEssay

    private val _totalPriceEssay = firstPrice.combine(secondPrice) {it1 , it2 ->
        Consts.DEFAULT_PRICE  + it2 + it1
    }
    val totalPriceEssay: LiveData<Long>
        get() = _totalPriceEssay

    private var content: String = ""

    fun setDeadlineEssay(deadline: Deadline) {
        secondPrice.postValue(deadline.deadline_price)
        secondName.postValue(deadline.deadline_name)
        _idDeadline = deadline.id
    }

    fun setLevelName(levelName: String) {
        Log.d("TAGG", "setLevelName: $levelName")
        _levelName.postValue(levelName)
    }

    fun setTopicName(topicName: String) {
        _topicName.postValue(topicName)
    }

    fun imgRecordStateClick() {
        Log.d("TAGG", "imgRecordStateClick: ")
        isRecording.value = isRecording.value?.not()
    }

    fun getDetailEssay(id_essay: Int) {
        _idEssay = id_essay
        Log.d(TAG, "getDetailTest called with id test = $id_essay")
        viewModelScope.launch {
            essayOfSystemUseCase.getEssayByID(id_essay).onStart {
                showLoadingDialog(true)
            }.catch { exception ->
                showLoadingDialog(false)
                Log.d(TAG, "getDetailTest: error with exception $exception")
            }.collect { baseResult ->
                showLoadingDialog(false)
                Log.d(TAG, "getDetailTest: success with data = $baseResult")

                _detailEssay.postValue(baseResult!!)
                Log.d(TAG, "getDetailTest: ${_detailEssay.value}")
            }
        }
    }

    private fun getAllDeadline() {
        Log.d(TAG, "getAllDeadline: ")
        viewModelScope.launch {
            orderUseCase.getDeadLineOrder().onStart {
                Log.d(TAG, "getAllDeadline: onstart()")
            }.catch { exception ->
                Log.d(TAG, "getDetailTest: error with exception $exception")
            }.collect {
                Log.d(TAG, "getAllDeadline: with data = $it")
                when (it) {
                    is Resource.Success -> {
                        _listDeadlineEssay.postValue(it.value!!)
                        Log.d(
                            TAG,
                            "getAllDeadline: post value success with ${_listDeadlineEssay.value}"
                        )
                    }
                }
            }
        }
    }

    fun navigateToInvoice(){
        navigate(R.id.action_paymentFragment_to_invoicePaymentDialog)
    }

    fun navigationWritingEssayFragmentToPaymentFragment(content: String?) {
        content?.let {
            this.content = content
            navigate(R.id.action_writingEssayFragment_to_paymentFragment)
        }
    }

    fun userSaveWrittenEssay() {
        Log.d(TAG, "userSaveWrittenEssay: ")
        val idUser = myPreference.getUserID()
        val idType = -1
        val saveEssay =
            totalPriceEssay.value?.let {
                Order(
                    id = UUID.randomUUID().toString(),
                    uid = myPreference.getUserID(),
                    dead_line = _idDeadline,
                    status_id = 0,
                    time_post = System.currentTimeMillis(),
                    content = content,
                    price = it,
                    essay_id = _idEssay
                )
            }
        Log.d(TAG, "userSaveWrittenEssay: saveEssay = $saveEssay")
        viewModelScope.launch {
            saveEssay?.let {
                orderUseCase.setUpEssayOrder(it).onStart {
                    Log.d(TAG, "userSaveWrittenEssay: onstart()")
                    showLoadingDialog(true)
                }.catch { exception ->
                    Log.d(TAG, "getDetailTest: error with exception $exception")
                    showLoadingDialog(false)
                }.collect { it1 ->
                    showLoadingDialog(false)
                    if (it1) {
                        viewModelScope.launch {
                            evenSender.send(CommonEvent.OnShowToast("Post order successfully"))
                            navigate(R.id.action_invoicePaymentDialog_to_myEssayFragment)
                        }
                    }
                }
            }
        }
    }
}
