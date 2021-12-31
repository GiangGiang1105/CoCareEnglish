package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.domain.essay.usecase.TestByTopicUseCase
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.listTemplate.ItemListModel
import com.example.cocarelish.utils.listTemplate.ItemListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EssaysByTopicViewModel @Inject constructor(
    private val testByTopicUseCase: TestByTopicUseCase,
    application: Application
) : CommonViewModel(application) {

    var listData = MutableLiveData<List<ItemListModel>>()
        private set

    private var mListItemListModel = mutableListOf<ItemListModel>()

    fun getAllTestByTopic(id_topic: Int) {
        Log.d(TAG, "getAllTestByTopic called with id topic = $id_topic")
        viewModelScope.launch {
            testByTopicUseCase.execute(id_topic).onStart {
                showLoadingDialog(true)
            }.catch { exception ->
                showLoadingDialog(false)
                Log.d(TAG, "getAllTestByTopic: error with exception: $exception")
            }.collect { baseResult ->
                showLoadingDialog(false)
                Log.d(TAG, "getAllTestByTopic: success with data = $baseResult")
                when (baseResult) {
                    is Resource.Success -> {
                        for (item in baseResult.value.tests) {
                            mListItemListModel.add(
                                ItemListModel(
                                    itemListType = ItemListType.ITEM_LIST_ESSAY_BY_TOPIC,
                                    message = item.question,
                                    id = item.id
                                )
                            )
                        }
                        listData.postValue(mListItemListModel)
                    }
                }
            }
        }
    }
}