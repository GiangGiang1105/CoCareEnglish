package com.example.cocarelish.utils.listTemplate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.databinding.ItemTaskBinding

class MenuAdapter(private val viewModel: CommonViewModel) : ListAdapter<MenuItem, MenuAdapter.ItemViewHolder>(MenuItemDiffCallback) {
    class ItemViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: MenuItem, viewModel: CommonViewModel) {
            binding.root.setOnClickListener{
                viewModel.onNavigate(menuItem.titleID)
            }
            binding.apply {
                this.menuItem = menuItem

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }
}