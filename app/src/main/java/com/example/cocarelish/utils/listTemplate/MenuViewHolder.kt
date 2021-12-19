package com.example.cocarelish.utils.listTemplate

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class MenuViewHolder<T : Any, VM : Any?>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    abstract fun bindData(data: T, viewModel: VM)
}