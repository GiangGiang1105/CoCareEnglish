package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.essay.remote.dto.Level
import com.example.cocarelish.data.essay.remote.dto.Type
import com.example.cocarelish.domain.essay.usecase.LevelUseCase
import com.example.cocarelish.domain.essay.usecase.TypeUseCase
import com.example.cocarelish.presentation.essay.fragments.TopicFragment
import com.example.cocarelish.utils.Consts
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.Title.HOME_WORK
import com.example.cocarelish.utils.Title.IELTS_WRITING_TASK_1
import com.example.cocarelish.utils.Title.IELTS_WRITING_TASK_2
import com.example.cocarelish.utils.listTemplate.ItemListModel
import com.example.cocarelish.utils.listTemplate.ItemListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelViewModel @Inject constructor(
    private val levelUseCase: LevelUseCase,
    private val typeUseCase: TypeUseCase,
    application: Application
) : CommonViewModel(application) {

    private val _listLevels: MutableLiveData<List<Level>> = MutableLiveData()
    val listLevels: LiveData<List<Level>> = _listLevels

    private val _listTypes : MutableLiveData<List<Type>> = MutableLiveData()
    val listTypes: LiveData<List<Type>> = _listTypes
    private val _isVisibleType: MutableLiveData<Boolean> = MutableLiveData(false)
    val isVisibleType : LiveData<Boolean> = _isVisibleType

    private var mListItemListModel = mutableListOf<ItemListModel>()
    val listMenuItem = listOf(
        ItemListModel(titleID = IELTS_WRITING_TASK_1, itemListType = ItemListType.ITEM_LEVEL),
        ItemListModel(titleID = IELTS_WRITING_TASK_2, itemListType = ItemListType.ITEM_LEVEL),
        ItemListModel(titleID = HOME_WORK, itemListType = ItemListType.ITEM_LEVEL),
    )

    fun getAllLevels() {

        viewModelScope.launch {
            levelUseCase.execute().onStart {
               // showLoadingDialog(true)
            }.catch { exeption ->
              //  showLoadingDialog(false)
                Log.d(TAG, "Get Levels Extension Error: ${exeption.message}")
            }.collect { baseResult ->
              //  showLoadingDialog(false)
                Log.d(TAG, "getAllLevels: $baseResult")
                when (baseResult) {
                    is Resource.Success-> _listLevels.postValue(baseResult.value.levels)
                }
            }
        }
    }

    fun getAllTypes(){

        viewModelScope.launch {
            typeUseCase.execute().onStart {
               // showLoadingDialog(true)
            }.catch {
                    exeption ->
               // showLoadingDialog(false)
                Log.d(TAG, "Get Types Extension Error: ${exeption.message}")
            }.collect {baseResult ->
               // showLoadingDialog(false)
                Log.d(TAG, "getAllTypes: $baseResult")
                when (baseResult) {
                    is Resource.Success-> _listTypes.postValue(baseResult.value.types)
                }
            }
        }
    }

    fun onClickNormal(){
        Log.d(TAG, "onClickNormal(): called with direct to normal topic")
        navigate(R.id.action_levelFragment_to_topicFragment, bundle =  bundleOf(TopicFragment.ARG_ID_TOPIC to 3))
    }

    fun onClickIeltsWriting(){
        _isVisibleType.postValue(_isVisibleType.value?.not())
        Log.d(TAG, "onClickIeltsWriting: ")
    }

    fun onClickIeltsWritingTask1(){
        Log.d("TAGG", "onClickIeltsWritingTask1(): called with direct to normal topic")
        navigate(R.id.action_levelFragment_to_topicFragment, bundle =  bundleOf(TopicFragment.ARG_ID_TOPIC to 1))
    }

    fun onClickIeltsWritingTask2(){
        Log.d(TAG, "onClickIeltsWritingTask2(): called with direct to normal topic")
        navigate(R.id.action_levelFragment_to_topicFragment, bundle =  bundleOf(TopicFragment.ARG_ID_TOPIC to 2))
    }
}