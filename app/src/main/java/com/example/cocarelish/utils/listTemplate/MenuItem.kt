package com.example.cocarelish.utils.listTemplate

import androidx.recyclerview.widget.DiffUtil

data class MenuItem (var titleID: Int = 0, var message: String = "")

object MenuItemDiffCallback : DiffUtil.ItemCallback<MenuItem>() {
    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem) =
        oldItem.titleID == newItem.titleID
    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem) =
        oldItem == newItem
}
