package com.example.cocarelish.presentation.grade.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.essay.remote.dto.Test
import com.example.cocarelish.data.order.dto.DetailResult
import com.example.cocarelish.data.order.dto.Order
import com.example.cocarelish.data.order.dto.OrderResult
import com.example.cocarelish.data.order.repository.OrderRepositoryImpl.Companion.STATUS_DONE
import com.example.cocarelish.domain.essay.usecase.EssayOfSystemUseCase
import com.example.cocarelish.domain.order.OrderRepository
import com.example.cocarelish.presentation.grade.adapter.Sentence
import com.example.cocarelish.utils.MyPreference
import com.example.cocarelish.utils.base.CommonCollapseEssayTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GradeAndJudgeViewModel @Inject constructor(
    application: Application,
    private val orderRepository: OrderRepository,
    private val essayOfSystemUseCase: EssayOfSystemUseCase,
    private val myPreference: MyPreference
) :
    CommonViewModel(application), CommonCollapseEssayTitle {

    private val _detailEssay = MutableLiveData<Test>()
    override var isCollapse= MutableLiveData(false)
        private set
    override val detailEssay: LiveData<Test>
        get() = _detailEssay

    private var mOrderID: String = ""
    var orderResult = MutableLiveData(OrderResult())

    var detailResult = MutableLiveData(DetailResult())
    var thisOrder = MutableLiveData(Order())
    var listSentenceReview = MutableLiveData(listOf<Sentence>())

    fun setOrderID(orderID: String) {
        mOrderID = orderID
        getOrderByOrderID()
    }

    private fun getOrderDetail() = viewModelScope.launch {
        orderRepository.getOrderResultByOrderID(mOrderID).onStart {
            showLoadingDialog(true)
        }.collect {
            orderResult.value = it

            if (it.detail_result_id.isNotBlank()) {
                getDetailResult(it.detail_result_id)
            }
        }
    }

    private fun getDetailResult(resultID: String) = viewModelScope.launch {
        orderRepository.getDetailResultByID(resultID).collect {
            detailResult.value = it
        }
    }

    private fun getDetailEssay(essayID: Int) = viewModelScope.launch {
        essayOfSystemUseCase.getEssayByID(essayID).collect {
            showLoadingDialog(false)
            _detailEssay.value = it
            myPreference.saveImageLink(it.image_link)
        }
    }

    private fun getOrderByOrderID() = viewModelScope.launch {
        orderRepository.getOrderByOrderId(mOrderID).collect {
            thisOrder.value = it
            getDetailEssay(it.essay_id)

            if(it.status_id == STATUS_DONE){
                getOrderDetail()
                listSentenceReview.value = listSentence
            }
        }
    }

    companion object{
        val listSentence = listOf(
            Sentence(orderSentence = 0, content = "abc", comment = "123"),
            Sentence(orderSentence = 1, content = "abcd", comment = "123"),
            Sentence(orderSentence = 2, content = "abcd", comment = "123"),
        )
    }
}
