package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.utils.Title
import com.example.cocarelish.utils.listTemplate.ItemListModel
import com.example.cocarelish.utils.listTemplate.ItemListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

class GeneralEssayViewModel(application: Application) : CommonViewModel(application) {

    val listData = listOf(
        ItemListModel(
            title = getString(Title.TITLE_NEW_ESSAY),
            itemListType = ItemListType.ITEM_GENERAL_ESSAY,
            titleID = Title.TITLE_NEW_ESSAY
        ),
        ItemListModel(
            title = getString(Title.TITLE_MY_ESSAY),
            itemListType = ItemListType.ITEM_GENERAL_ESSAY,
            titleID = Title.TITLE_MY_ESSAY
        )
    )

    override fun onNavigate(itemListModel: ItemListModel) {
        when (itemListModel.titleID) {
            Title.TITLE_MY_ESSAY -> {
                navigate(R.id.action_generalEssayFragment_to_myEssayFragment)
            }
            Title.TITLE_NEW_ESSAY -> {
                navigate(R.id.action_generalEssayFragment_to_levelFragment)
            }
        }
    }
}