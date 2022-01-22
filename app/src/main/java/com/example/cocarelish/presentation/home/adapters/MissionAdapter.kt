package com.example.cocarelish.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cocarelish.databinding.ItemMissionBinding
import com.example.cocarelish.domain.room.entity.Mission
import com.example.cocarelish.domain.room.entity.MissionItemDiffCallback
import com.example.cocarelish.utils.base.CommonItemMenuAction
import com.example.cocarelish.utils.listTemplate.MenuViewHolder

class MissionAdapter(private val viewModel: CommonItemMenuAction): ListAdapter<Mission, MenuViewHolder<Mission, CommonItemMenuAction>>(
    MissionItemDiffCallback
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder<Mission, CommonItemMenuAction> =
        ItemMissionHolder(parent)

    override fun onBindViewHolder(
        holder: MenuViewHolder<Mission, CommonItemMenuAction>,
        position: Int
    ) {
        holder.bindData(getItem(position), viewModel)
    }

    inner class ItemMissionHolder(
        private val parent: ViewGroup,
        private val binding: ItemMissionBinding = ItemMissionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ): MenuViewHolder<Mission, CommonItemMenuAction>(binding.root){
        override fun bindData(data: Mission, viewModel: CommonItemMenuAction) {
            binding.apply {
                mission = data
            }
        }
    }

}

