package com.example.cocarelish.utils.listTemplate

import androidx.recyclerview.widget.DiffUtil

data class ItemListModel(
    var itemListType: ItemListType,
    var titleID: Int = 0,
    var message: String = "",
    var title: String = "",
    var image: String = ""
)

enum class ItemListType {
    ITEM_LEVEL,
    ITEM_TOPIC
}

object MenuItemDiffCallback : DiffUtil.ItemCallback<ItemListModel>() {
    override fun areItemsTheSame(oldItemListModel: ItemListModel, newItemListModel: ItemListModel) =
        oldItemListModel.titleID == newItemListModel.titleID
                && oldItemListModel.image == newItemListModel.image
                && oldItemListModel.message == newItemListModel.message
                && oldItemListModel.itemListType == newItemListModel.itemListType
                && oldItemListModel.title == newItemListModel.title

    override fun areContentsTheSame(oldItemListModel: ItemListModel, newItemListModel: ItemListModel) =
        oldItemListModel == newItemListModel
}
