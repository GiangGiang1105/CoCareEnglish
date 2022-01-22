package com.example.cocarelish.presentation.order.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.cocarelish.data.essay.remote.dto.Deadline
import com.example.cocarelish.databinding.ItemDeadlineBinding
import com.example.cocarelish.utils.listTemplate.ItemListModel

class DeadlineAdapter : RecyclerView.Adapter<DeadlineAdapter.ItemDeadline>() {
    private val TAG by lazy { this::class.simpleName }

    private val _listDeadline: MutableList<Deadline> = mutableListOf()

    private var onItemCLickListener: ((deadline: Deadline) -> Unit)? = null

    private var lastCheckedPosition = -1

    fun setOnClickListener(listener: ((deadline: Deadline) -> Unit)) {
        onItemCLickListener = listener
    }

    fun setData(listDeadline: List<Deadline>) {
        listDeadline?.let {
            _listDeadline.clear()
            _listDeadline.addAll(it)
        }
        notifyDataSetChanged()

        Log.d(TAG, "setData: $_listDeadline")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDeadline = ItemDeadline(
        ItemDeadlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ItemDeadline, position: Int) {
        holder.bindData(_listDeadline[position])
        holder.radioButton.isChecked = position == lastCheckedPosition
    }

    override fun getItemCount(): Int = _listDeadline.size

    inner class ItemDeadline(private val binding: ItemDeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val radioButton: RadioButton = binding.radioDeadline

        fun bindData(dl: Deadline) {
            binding.apply {
                this.deadline = dl
                radioDeadline.setOnClickListener {
                    lastCheckedPosition = adapterPosition
                    onItemCLickListener?.invoke(dl)
                    notifyDataSetChanged()
                }
            }
        }
    }
}