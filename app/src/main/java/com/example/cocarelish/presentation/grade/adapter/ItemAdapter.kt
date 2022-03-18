package com.example.cocarelish.presentation.grade.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocarelish.databinding.ItemSentenceFixBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemSentence>() {
    private val TAG by lazy { this::class.simpleName }

    private val _listSentence: MutableList<Sentence> = mutableListOf()

    private var onItemCLickListener: ((sentence: Sentence) -> Unit)? = null

    fun setOnClickListener(listener: ((sentence: Sentence) -> Unit)) {
        onItemCLickListener = listener
    }

    fun setData(listDeadline: List<Sentence>) {
        listDeadline.let {
            _listSentence.clear()
            _listSentence.addAll(it)
        }
        notifyDataSetChanged()

        Log.d(TAG, "setData: $_listSentence")
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSentence {
        return ItemSentence(ItemSentenceFixBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemSentence, position: Int) {
        holder.bindData(_listSentence[position])
    }

    override fun getItemCount(): Int = _listSentence.size

    inner class ItemSentence(private val binding: ItemSentenceFixBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bindData(dl: Sentence) {
            binding.apply {
                sentence = dl
                position = "Id: ${(layoutPosition + 1)}"
            }
        }
    }
}