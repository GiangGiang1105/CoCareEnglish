package com.example.cocarelish.presentation.grade.viewmodel

import android.app.Application
import androidx.lifecycle.*
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
    override var isCollapse = MutableLiveData(false)
        private set
    override val detailEssay: LiveData<Test>
        get() = _detailEssay

    private var mOrderID: String = ""
    var orderResult = MutableLiveData(OrderResult())

    var detailResult = MutableLiveData(DetailResult())
    var thisOrder = MutableLiveData(Order())
    var oldContent = ""

    var currentSentenceReviewed = MutableLiveData(listSentences[0].orderSentence)

    fun getOrderSentence(position: Int){
        currentSentenceReviewed.postValue(listSentences[position].orderSentence)
    }

    var displayOrder = thisOrder.combine(currentSentenceReviewed) { order, current ->
        val list = oldContent.split(sentenceRegex)
        var strContent = oldContent
        listSentences.forEach {
            if (it.orderSentence < list.count()) {
                strContent = if (current != it.orderSentence) strContent.replace(
                    list[it.orderSentence],
                    "<FONT COLOR=\"#ff0000\">${list[it.orderSentence]}</FONT>"
                )
                else {
                    strContent.replace(
                        list[it.orderSentence],
                        "<FONT COLOR=\"#ff0000\"><strong><i>${list[it.orderSentence]}</i></strong></FONT>"
                    )
                }
            }
        }
        order.content = strContent
        order
    }


    private val sentenceRegex = "(?<=\\.\\s)|(?<=[?!]\\s)".toRegex()

    var listSentenceReview = thisOrder.map{ order ->
        val listSentence = mutableListOf<Sentence>()
        val list = oldContent.split(sentenceRegex)
        listSentences.forEach {
            it.content = list[it.orderSentence]
            listSentence.add(it)
        }
        listSentence
    }

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
            oldContent = it.content
            thisOrder.value = it
            getDetailEssay(it.essay_id)

            if (it.status_id == STATUS_DONE) {
                getOrderDetail()
            }
        }
    }

    companion object {
        val listSentences = listOf(
            Sentence(orderSentence = 0, content = "abc", comment = "Sentences need to be fluent, change the word \"car\" to the plural form \"cars\"."),
            Sentence(orderSentence = 3, content = "abcd", comment = "This sentence must be in the passive voice instead of \"stealing\" to \"stolen\"."),
            Sentence(orderSentence = 4, content = "abcd", comment = "\"slightly\" will be proper"),
        )
    }
}

inline fun <T1, T2, R> LiveData<T1>.combine(
    liveData2: LiveData<T2>,
    crossinline transform: (T1, T2) -> R
): LiveData<R> = combineLiveData(this, liveData2) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2
    )
}

inline fun <R> combineLiveData(
    vararg varargLiveData: LiveData<*>,
    crossinline transform: (Array<*>) -> R
) = MediatorLiveData<R>().apply {
    varargLiveData.forEach { liveData ->
        addSource(liveData) {
            val listDataCallback = varargLiveData.map {
                it.value ?: return@addSource
            }.toTypedArray()
            value = transform(listDataCallback)
        }
    }
}