package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.domain.essay.usecase.EssayOfUserUseCase
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
class MyEssayViewModel @Inject constructor(
    private val essayOfUserUseCase: EssayOfUserUseCase,
    application: Application
) : CommonViewModel(application) {
    private var mListItemMyEssay = mutableListOf<ItemListModel>()
    private val _listData: MutableLiveData<List<ItemListModel>> = MutableLiveData()
    val listData: LiveData<List<ItemListModel>>
        get() = _listData

    fun getAllEssayOfUser(user_id: String) {
        Log.d(TAG, "getAllEssayOfUser called with id user = $user_id")
        viewModelScope.launch {
            essayOfUserUseCase.execute(user_id).onStart {
                Log.d(TAG, "getAllEssayOfUser: onStart")
                showLoadingDialog(true)
            }.catch { exception ->
                showLoadingDialog(false)
                Log.d(TAG, "getAllEssayOfUser: error with exception: $exception")
            }.collect { baseResult ->
                Log.d(TAG, "getAllTestByTopic: true")
                showLoadingDialog(false)
                Log.d(TAG, "getAllEssayOfUser: success with data = $baseResult")
                when (baseResult) {
                    is Resource.Success -> {
                        mListItemMyEssay.clear()
                        for (item in baseResult.value.orders) {
                            mListItemMyEssay.add(
                                ItemListModel(
                                    itemListType = ItemListType.ITEM_LIST_MY_ESSAY,
                                    status = item.status,
                                    id = item.id,
                                    type_name = item.type_name,
                                    question_of_test = item.question_of_test,
                                    content = item.content,
                                    teacher_name = item.teacher_name,
                                    updated_at = item.updated_at,
                                    score = item.score,

                                )
                            )
                        }
                        _listData.postValue(mListItemMyEssay)
                    }
                }
            }
        }
    }
}