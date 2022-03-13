package com.example.cocarelish.presentation.grade.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.domain.essay.usecase.EssayOfSystemUseCase
import com.example.cocarelish.domain.order.OrderRepository
import com.example.cocarelish.utils.MyPreference
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
    private val orderRepository: OrderRepository,
    private val myPreference: MyPreference,
    application: Application
) : CommonViewModel(application) {
    init{
        getAllEssayOfUser()
    }

    private var mListItemMyEssay = mutableListOf<ItemListModel>()
    private val _listData: MutableLiveData<List<ItemListModel>> = MutableLiveData()
    val listData: LiveData<List<ItemListModel>>
        get() = _listData

    private fun getAllEssayOfUser() {
        viewModelScope.launch {
            orderRepository.getAllOrderByUserId(myPreference.getUserID()).onStart {
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
                        _listData.postValue(baseResult.value!!)
                    }
                }
            }
        }
    }
}