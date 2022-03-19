package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.essay.remote.dto.Level
import com.example.cocarelish.data.essay.remote.dto.Test
import com.example.cocarelish.data.essay.remote.dto.Topic
import com.example.cocarelish.domain.essay.usecase.EssayOfSystemUseCase
import com.example.cocarelish.presentation.essay.fragments.EssaysByTopicFragment
import com.example.cocarelish.presentation.essay.fragments.WritingEssayFragment
import com.example.cocarelish.utils.LinkImage
import com.example.cocarelish.utils.MyPreference
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.Title
import com.example.cocarelish.utils.base.CommonCollapseEssayTitle
import com.example.cocarelish.utils.listTemplate.ItemListModel
import com.example.cocarelish.utils.listTemplate.ItemListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EssayViewModel @Inject constructor(
    private val essayOfUserUseCase: EssayOfSystemUseCase,
    private val myPreference: MyPreference,
    application: Application
) : CommonViewModel(application), CommonCollapseEssayTitle {

    init {
        getAllLevels()
        getAllTopic()
    }

    override var isCollapse = MutableLiveData(false)
        private set

    private val _isVisibleType: MutableLiveData<Boolean> = MutableLiveData(false)
    val isVisibleType: LiveData<Boolean> = _isVisibleType

    private val _levelNameCurrent: MutableLiveData<String> = MutableLiveData()
    val levelName: LiveData<String> = _levelNameCurrent

    private val _topicNameCurrent: MutableLiveData<String> = MutableLiveData()
    val topicNameCurrent: LiveData<String> = _topicNameCurrent
    var currentTypeID = MutableLiveData(TYPE_NORMAL)

    var listDataTestByTopic = MutableLiveData<List<ItemListModel>?>(mutableListOf())
        private set

    var listDataTopic = currentTypeID.map { currentTypeID ->
        listTopic.value?.filter {
            it.type_id == currentTypeID
        }?.map { item ->
            ItemListModel(
                title = item.name,
                itemListType = ItemListType.ITEM_TOPIC,
                message = item.description,
                image = mapListImageTopic[item.id] ?: LinkImage.LINE_GRAPH,
                id = item.id,
            )
        }
    }

    private var mListItemTestByTopic = mutableListOf<ItemListModel>()

    private val _listLevels: MutableLiveData<List<Level>?> = MutableLiveData()

    private val _listTopic: MutableLiveData<List<Topic>> = MutableLiveData()
    private val listTopic: LiveData<List<Topic>> = _listTopic

    private val _detailEssay = MutableLiveData<Test>()
    override val detailEssay: LiveData<Test>
        get() = _detailEssay

    override fun changeStateCollapseView() {
        super.changeStateCollapseView()
        Log.d("TAGG", "changeStateCollapseView: ")
    }

    private fun getAllLevels() = viewModelScope.launch {
        essayOfUserUseCase.getAllLevel().onStart {
            //                 showLoadingDialog(true)
        }.catch {
            //                 showLoadingDialog(true)
        }.collect { resource ->
            when (resource) {
                is Resource.Success -> _listLevels.postValue(resource.value)
            }
        }
    }

    private fun getAllTopic() {
        viewModelScope.launch {
            essayOfUserUseCase.getAllType().onStart {
                showLoadingDialog(true)
            }.catch { exception ->
                showLoadingDialog(false)
                Log.d(TAG, "Get Types Extension Error: ${exception.message}")
            }.collect { baseResult ->
                showLoadingDialog(false)
                Log.d(TAG, "getAllTypes: $baseResult")
                when (baseResult) {
                    is Resource.Success -> {
                        Log.d(TAG, "getAllTypes: qwe")
                        _listTopic.postValue(baseResult.value!!)
                    }
                }
            }
        }
    }

    fun onClickNormal() {
        Log.d(TAG, "onClickNormal(): called with direct to normal topic")
        listTopic.value?.get(TYPE_NORMAL)?.id?.let {
            saveTypeId(it.toString())
        }

        currentTypeID.postValue(TYPE_NORMAL)

        navigate(
            R.id.action_levelFragment_to_topicFragment,
        )
    }

    fun onClickIeltsWriting() {
        _isVisibleType.postValue(_isVisibleType.value?.not())
        Log.d(TAG, "onClickIeltsWriting: ")
    }

    fun onClickIeltsWritingTask1() {
        Log.d("TAGG", "onClickIeltsWritingTask1(): called with direct to normal topic")
        _levelNameCurrent.postValue(listTopic.value?.get(0)?.name)
        currentTypeID.postValue(TYPE_IELTS_1)
        navigate(
            R.id.action_levelFragment_to_topicFragment,
        )
    }

    fun onClickIeltsWritingTask2() {
        Log.d(TAG, "onClickIeltsWritingTask2(): called with direct to normal topic")
        _levelNameCurrent.postValue(listTopic.value?.get(1)?.name)
        currentTypeID.postValue(TYPE_IELTS_2)
        navigate(
            R.id.action_levelFragment_to_topicFragment,
        )
    }

    private var listTestByTopicID = listOf<Test>()

    fun getAllTestByTopic(id_topic: Int) {
        Log.d(TAG, "getAllTestByTopic called with id topic = $id_topic")
        viewModelScope.launch {
            essayOfUserUseCase.getAllQuestionByTopicID(id_topic).onStart {
                Log.d(TAG, "getAllTestByTopic: onStart")
                showLoadingDialog(true)
            }.catch { exception ->
                showLoadingDialog(false)
                Log.d(TAG, "getAllTestByTopic: error with exception: $exception")
            }.collect { baseResult ->
                Log.d(TAG, "getAllTestByTopic: true")
                showLoadingDialog(false)
                Log.d(TAG, "getAllTestByTopic: success with data = $baseResult")
                when (baseResult) {
                    is Resource.Success -> {
                        if (baseResult.value.isNotEmpty()) {
                            listTestByTopicID = baseResult.value!!
                            mListItemTestByTopic.clear()
                            for (item in baseResult.value) {
                                mListItemTestByTopic.add(
                                    ItemListModel(
                                        itemListType = ItemListType.ITEM_LIST_ESSAY_BY_TOPIC,
                                        message = item.question,
                                        id = item.id,
                                        topic_id = item.topic_id
                                    )
                                )
                            }
                            listDataTestByTopic.postValue(mListItemTestByTopic)
                        }
                    }
                }
            }
        }
    }

    fun onClickLetsGo() {
        Log.d(TAG, "onClickLetsGo: with value id = ${detailEssay.value?.id}")
        navigate(
            R.id.action_showDetailEssayTitleFragment_to_writingEssayFragment, bundleOf(
                WritingEssayFragment.ARG_ID_ESSAY to currentDetailEssayId,
                WritingEssayFragment.ARG_LEVEL_NAME to mapType[currentTypeID.value],
                WritingEssayFragment.ARG_TOPIC_NAME to topicNameCurrent.value
            )
        )
    }

    var currentDetailEssayId = 0

    override fun onNavigate(itemListModel: ItemListModel) {
        when (itemListModel.itemListType) {
            ItemListType.ITEM_LIST_ESSAY_BY_TOPIC -> {
                Log.d(TAG, "onNavigate: ${itemListModel.id}")
                currentDetailEssayId = itemListModel.id
                val currentEssay = listTestByTopicID.find { it.id == itemListModel.id }
                currentEssay?.let {
                    _detailEssay.postValue(it)
                    myPreference.saveImageLink(it.image_link)
                }
                navigate(
                    R.id.action_essaysByTopicFragment_to_showDetailEssayTitleFragment,
                )
            }
            ItemListType.ITEM_TOPIC -> {
                _topicNameCurrent.postValue(itemListModel.title)
                navigate(
                    R.id.action_topicFragment_to_essaysByTopicFragment,
                    bundle = bundleOf(
                        EssaysByTopicFragment.ARG_ID_TOPIC to itemListModel.id,
                        EssaysByTopicFragment.ARG_NAME_TOPIC to itemListModel.title
                    )
                )
            }

            else -> {
                Log.d(TAG, "onNavigate: not found item list type")
            }
        }
    }

    private fun saveTypeId(id_type: String) {
        myPreference.saveUserID(id_type)
    }

    val textSize = MutableLiveData(50)


    fun setTextSize(size: Int) {
        textSize.value = size
    }


    companion object {

        private const val TYPE_NORMAL = 0
        private const val TYPE_IELTS_1 = 1
        private const val TYPE_IELTS_2 = 2

        val mapType = hashMapOf(
            TYPE_NORMAL to Title.TYPE_NORMAL,
            TYPE_IELTS_1 to Title.TYPE_IELTS_WRITING_TASK_1,
            TYPE_IELTS_2 to Title.TYPE_IELTS_WRITING_TASK_2
        )


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
}