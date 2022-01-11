package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.essay.remote.dto.Level
import com.example.cocarelish.data.essay.remote.dto.Test
import com.example.cocarelish.data.essay.remote.dto.Type
import com.example.cocarelish.domain.essay.usecase.*
import com.example.cocarelish.presentation.essay.fragments.EssaysByTopicFragment
import com.example.cocarelish.presentation.essay.fragments.ShowDetailTitleEssayFragment
import com.example.cocarelish.presentation.essay.fragments.TopicFragment
import com.example.cocarelish.presentation.essay.fragments.WritingEssayFragment
import com.example.cocarelish.utils.LinkImage
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
    private val testByTopicUseCase: TestByTopicUseCase,
    private val topicUseCase: TopicUseCase,
    private val levelUseCase: LevelUseCase,
    private val typeUseCase: TypeUseCase,
    private val testUseCase: TestUseCase,
    application: Application
) : CommonViewModel(application), CommonCollapseEssayTitle {

    override var isCollapse = MutableLiveData(false)
        private set

    private val _isVisibleType: MutableLiveData<Boolean> = MutableLiveData(false)
    val isVisibleType: LiveData<Boolean> = _isVisibleType

    private val _levelNameCurrent: MutableLiveData<String> = MutableLiveData()
    val levelName: LiveData<String> = _levelNameCurrent

    private val _topicNameCurrent: MutableLiveData<String> = MutableLiveData()
    val topicNameCurrent: LiveData<String> = _topicNameCurrent

    var listDataTopic = MutableLiveData<List<ItemListModel>>(mutableListOf())
        private set
    var listDataTestByTopic = MutableLiveData<List<ItemListModel>>(mutableListOf())
        private set

    val emptyList = mutableListOf<ItemListModel>()

    var listData = MediatorLiveData<List<ItemListModel>>().apply {
        addSource(listDataTopic) {
            value = it
        }
        addSource(listDataTestByTopic) {
            value = it
        }
    }

    private var mListItemTestByTopic = mutableListOf<ItemListModel>()
    private var mListItemTopic = mutableListOf<ItemListModel>()
    private var mListItemListModel = mutableListOf<ItemListModel>()
    val listMenuItem = listOf(
        ItemListModel(titleID = Title.IELTS_WRITING_TASK_1, itemListType = ItemListType.ITEM_LEVEL),
        ItemListModel(titleID = Title.IELTS_WRITING_TASK_2, itemListType = ItemListType.ITEM_LEVEL),
        ItemListModel(titleID = Title.HOME_WORK, itemListType = ItemListType.ITEM_LEVEL),
    )

    private val _listLevels: MutableLiveData<List<Level>> = MutableLiveData()
    val listLevels: LiveData<List<Level>> = _listLevels

    private val _listTypes: MutableLiveData<List<Type>> = MutableLiveData()
    val listTypes: LiveData<List<Type>> = _listTypes

    private val _detailEssay = MutableLiveData<Test>()
    override val detailEssay: LiveData<Test>
        get() = _detailEssay

    override fun changeStateCollapseView() {
        super.changeStateCollapseView()
        Log.d("TAGG", "changeStateCollapseView: ")
    }

    fun getAllLevels() {

        viewModelScope.launch {
            levelUseCase.execute().onStart {
//                 showLoadingDialog(true)
            }.catch { exeption ->
//                  showLoadingDialog(false)
                Log.d(TAG, "Get Levels Extension Error: ${exeption.message}")
            }.collect { baseResult ->
//                  showLoadingDialog(false)
                Log.d(TAG, "getAllLevels: $baseResult")
                when (baseResult) {
                    is Resource.Success -> _listLevels.postValue(baseResult.value.levels)
                }
            }
        }
    }

    fun getAllTypes() {
        viewModelScope.launch {
            typeUseCase.execute().onStart {
                showLoadingDialog(true)
            }.catch { exeption ->
                showLoadingDialog(false)
                Log.d(TAG, "Get Types Extension Error: ${exeption.message}")
            }.collect { baseResult ->
                showLoadingDialog(false)
                Log.d(TAG, "getAllTypes: $baseResult")
                when (baseResult) {
                    is Resource.Success -> {
                        Log.d(TAG, "getAllTypes: qwe")
                        _listTypes.postValue(baseResult.value.types)
                    }
                }
            }
        }
    }

    fun onClickNormal() {
        Log.d(TAG, "onClickNormal(): called with direct to normal topic")
        _levelNameCurrent.postValue(listLevels.value?.get(0)?.name)
        listData.value = emptyList
        navigate(
            R.id.action_levelFragment_to_topicFragment,
            bundle = bundleOf(TopicFragment.ARG_ID_TOPIC to 3)
        )
    }

    fun onClickIeltsWriting() {
        _isVisibleType.postValue(_isVisibleType.value?.not())
        Log.d(TAG, "onClickIeltsWriting: ")
    }

    fun onClickIeltsWritingTask1() {
        Log.d("TAGG", "onClickIeltsWritingTask1(): called with direct to normal topic")
        _levelNameCurrent.postValue(listTypes.value?.get(0)?.name)
        listData.value = emptyList
        navigate(
            R.id.action_levelFragment_to_topicFragment,
            bundle = bundleOf(TopicFragment.ARG_ID_TOPIC to 1)
        )
    }

    fun onClickIeltsWritingTask2() {
        Log.d(TAG, "onClickIeltsWritingTask2(): called with direct to normal topic")
        _levelNameCurrent.postValue(listTypes.value?.get(1)?.name)
        listData.value = emptyList
        navigate(
            R.id.action_levelFragment_to_topicFragment,
            bundle = bundleOf(TopicFragment.ARG_ID_TOPIC to 2)
        )
    }

    fun getAllTopics(id_topic: Int) {
        Log.d("TAGG", "getAllTopics: $id_topic")
        viewModelScope.launch {
            topicUseCase.execute(id_topic).onStart {
                Log.d(TAG, "getAllTopics: onStart")
                showLoadingDialog(true)
            }.catch { exeption ->
                showLoadingDialog(false)
                Log.d(TAG, "Get Topics Extension Error: ${exeption.message}")
            }.collect { baseResult ->
                Log.d(TAG, "getAllTopics: true")
                showLoadingDialog(false)
                Log.d(TAG, "getAllTopics: $baseResult")
                when (baseResult) {
                    is Resource.Success -> {
                        mListItemTopic.clear()
                        for (item in baseResult.value.topics) {
                            mListItemTopic.add(
                                ItemListModel(
                                    title = item.name,
                                    itemListType = ItemListType.ITEM_TOPIC,
                                    message = item.description,
                                    image = mapListImageTopic[item.id] as String,
                                    id = item.id
                                )
                            )
                        }
                        listDataTopic.value = mListItemTopic
                    }
                }
            }
        }
    }

    fun getAllTestByTopic(id_topic: Int) {
        Log.d(TAG, "getAllTestByTopic called with id topic = $id_topic")
        viewModelScope.launch {
            testByTopicUseCase.execute(id_topic).onStart {
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
                        mListItemTestByTopic.clear()
                        for (item in baseResult.value.tests) {
                            mListItemTestByTopic.add(
                                ItemListModel(
                                    itemListType = ItemListType.ITEM_LIST_ESSAY_BY_TOPIC,
                                    message = item.question,
                                    id = item.id
                                )
                            )
                        }
                        listDataTestByTopic.postValue(mListItemTestByTopic)
                    }
                }
            }
        }
    }

    fun addTopicSource() {
//        listData.addSource(listDataTopic){
        listData.value = listDataTopic.value

    }

    fun addLevelSource() {
//        listData.addSource(listDataTestByTopic) {
        listData.value = listDataTestByTopic.value

    }

    fun onClickLetsGo() {
        Log.d(TAG, "onClickLetsGo: with value id = ${detailEssay.value?.id}")
        navigate(
            R.id.action_showDetailEssayTitleFragment_to_writingEssayFragment, bundleOf(
                WritingEssayFragment.ARG_ID_ESSAY to detailEssay.value?.id
            )
        )
    }

    override fun onNavigate(itemListModel: ItemListModel) {
        when (itemListModel.itemListType) {
            ItemListType.ITEM_LIST_ESSAY_BY_TOPIC -> {
                Log.d(TAG, "onNavigate: ${itemListModel.id}")
                navigate(
                    R.id.action_essaysByTopicFragment_to_showDetailEssayTitleFragment,
                    bundle = bundleOf(
                        ShowDetailTitleEssayFragment.ARG_ID_ESSAY to itemListModel.id
                    )
                )
            }
            ItemListType.ITEM_TOPIC -> {
//                listData.removeSource(listDataTopic)
                listData.value = emptyList
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

    fun getDetailTest(id_test: Int) {
        Log.d(TAG, "getDetailTest called with id test = $id_test")
        viewModelScope.launch {
            testUseCase.execute(id_test).onStart {
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
}