package com.example.cocarelish.utils.listTemplate

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class ItemListModel(
    var itemListType: ItemListType = ItemListType.ITEM_LIST_ESSAY_BY_TOPIC,
    var titleID: Int = 0,
    var message: String = "",
    var title: String = "",
    var image: String = "",
    var id: Int = -1,
    var isFavourite: Int = 0,
    var orderId: String = "",
    val user_id: Int = -1,
    val topic_id: Int = 0,
    val status: Int = 0,
    val type_name: String = "",
    val question_of_test: String = "",
    val teacher_name: String? = "",
    val content: String = "",
    var score: Int = -1,
    val updated_at: String = "",
    val status_id: Int = -1,
    val essay_id: Int = -1 ,
    val price: Long = 0
)

enum class ItemListType {
    ITEM_LEVEL,
    ITEM_TOPIC,
    ITEM_LIST_ESSAY_BY_TOPIC,
    ITEM_LIST_MY_ESSAY,
    ITEM_GENERAL_ESSAY
}

object MenuItemDiffCallback : DiffUtil.ItemCallback<ItemListModel>() {
    override fun areItemsTheSame(oldItemListModel: ItemListModel, newItemListModel: ItemListModel) =
        oldItemListModel.titleID == newItemListModel.titleID
                && oldItemListModel.image == newItemListModel.image
                && oldItemListModel.message == newItemListModel.message
                && oldItemListModel.itemListType == newItemListModel.itemListType
                && oldItemListModel.title == newItemListModel.title

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItemListModel: ItemListModel,
        newItemListModel: ItemListModel
    ) =
        oldItemListModel == newItemListModel
}
