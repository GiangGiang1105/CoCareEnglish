package com.example.cocarelish.utils.listTemplate

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.cocarelish.R
import com.example.cocarelish.application.MyApplication.Companion.context
import com.example.cocarelish.databinding.ItemTaskBinding
import com.example.cocarelish.databinding.ItemTopicBinding
import com.example.cocarelish.utils.base.CommonItemMenuAction
import com.example.cocarelish.utils.databinding.loadImage
import java.lang.IllegalArgumentException
import kotlin.math.roundToInt

class MenuAdapter(private val viewModel: CommonItemMenuAction) :
    ListAdapter<ItemListModel, MenuViewHolder<ItemListModel, CommonItemMenuAction>>(
        MenuItemDiffCallback
    ) {

    private val TAG by lazy { this::class.simpleName }

    inner class ItemLevelHolder(
        private val parent: ViewGroup,
        private val binding: ItemTaskBinding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : MenuViewHolder<ItemListModel, CommonItemMenuAction>(binding.root) {

        override fun bindData(data: ItemListModel, viewModel: CommonItemMenuAction) {
            Log.d(TAG, "bindData: ItemLevelHolder $data")
            binding.root.setOnClickListener {
                viewModel.onNavigate(data)
            }
            binding.apply {
                this.menuItem = data
            }
        }
    }

    inner class ItemTopicHolder(
        private val parent: ViewGroup,
        private val binding: ItemTopicBinding = ItemTopicBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) :
        MenuViewHolder<ItemListModel, CommonItemMenuAction>(binding.root) {

        override fun bindData(data: ItemListModel, viewModel: CommonItemMenuAction) {
            Log.d(TAG, "bindData: ItemTopicHolder")
            binding.root.setOnClickListener {
                viewModel.onNavigate(data)
            }
            binding.topicConstraint.background.alpha =125
            binding.menuItem = data
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder<ItemListModel, CommonItemMenuAction> = when (viewType) {
        ItemListType.ITEM_LEVEL.ordinal -> {
            Log.e(TAG, "onCreateViewHolder: ItemLevelHolder")
            ItemLevelHolder(parent)
        }
        ItemListType.ITEM_TOPIC.ordinal -> {
            Log.e(TAG, "onCreateViewHolder: ItemTopicHolder")
            ItemTopicHolder(parent)
        }
        else -> throw IllegalArgumentException("Not found viewType...")
    }

    override fun onBindViewHolder(
        holder: MenuViewHolder<ItemListModel, CommonItemMenuAction>,
        position: Int
    ) {
        Log.d(TAG, "onBindViewHolder: $holder")
        holder.bindData(getItem(position), viewModel)
    }

    override fun getItemViewType(position: Int): Int =
        currentList[position].itemListType.ordinal

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long = position.toLong()

    fun submit(dataList: List<ItemListModel>) {
        Log.d(TAG, "submit: ${dataList.size}")
        submitList(dataList)
    }
}