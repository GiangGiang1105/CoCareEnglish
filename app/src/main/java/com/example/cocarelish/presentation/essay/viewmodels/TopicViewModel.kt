package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.domain.essay.usecase.TopicUseCase
import com.example.cocarelish.presentation.essay.fragments.EssaysByTopicFragment
import com.example.cocarelish.utils.LinkImage
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
class TopicViewModel @Inject constructor(
    private val topicUseCase: TopicUseCase,
    application: Application
) : CommonViewModel(application) {

    var listData = MutableLiveData<List<ItemListModel>>()
        private set

    private var mListItemListModel = mutableListOf<ItemListModel>()

    fun getAllTopics(id_topic: Int) {
        Log.d("TAGG", "getAllTopics: $id_topic")
        viewModelScope.launch {
            topicUseCase.execute(id_topic).onStart {
                showLoadingDialog(true)
            }.catch { exeption ->
                showLoadingDialog(false)
                Log.d(TAG, "Get Topics Extension Error: ${exeption.message}")
            }.collect { baseResult ->
                showLoadingDialog(false)
                Log.d(TAG, "getAllTopics: $baseResult")
                when (baseResult) {
                    is Resource.Success -> {
                        for (item in baseResult.value.topics) {
                            mListItemListModel.add(
                                ItemListModel(
                                    title = item.name,
                                    itemListType = ItemListType.ITEM_TOPIC,
                                    message = item.description,
                                    image = mapListImageTopic[item.id] as String,
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

    companion object {
        private val mapListImageTopic = hashMapOf(
            1 to LinkImage.LINE_GRAPH,
            2 to LinkImage.PIE_CHART,
            3 to LinkImage.BAR_CHART,
            4 to LinkImage.TABLE,
            5 to LinkImage.MIXED_CHART,
            6 to LinkImage.PROCESS,
            7 to LinkImage.MAPS,
            8 to LinkImage.ARGUMENTATIVE,
            9 to LinkImage.DISCUSSION,
            10 to LinkImage.PROGRAM_SOLUTION,
            11 to LinkImage.ADVANTAGE_DISADVANTAGE,
            12 to LinkImage.TWO_PART_QUESTION,
            13 to LinkImage.EDUCTION,
            14 to LinkImage.ENVIROMENT,
            15 to LinkImage.ART,
            16 to LinkImage.CHILDREN,
            17 to LinkImage.FAMILY
        )
    }

    override fun onNavigate(itemListModel: ItemListModel) {
        navigate(
            R.id.action_topicFragment_to_essaysByTopicFragment,
            bundle = bundleOf(
                EssaysByTopicFragment.ARG_ID_TOPIC to itemListModel.id,
                EssaysByTopicFragment.ARG_NAME_TOPIC to itemListModel.title
            )
        )
    }
}