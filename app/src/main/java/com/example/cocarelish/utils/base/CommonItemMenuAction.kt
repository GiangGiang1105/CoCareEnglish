package com.example.cocarelish.utils.base

import android.util.Log
import com.example.cocarelish.utils.listTemplate.ItemListModel

interface CommonItemMenuAction {
    fun onLongClick(idItem: Int, drawableId: Int?, isFavorite: Boolean, clickable: Boolean): Boolean {
        return clickable
    }

    fun onClickClose() {
        Log.d(TAG, "onClickClose")
    }

    fun onClicked(itemTitle: Int) {
        Log.d(TAG, "onClicked")
    }

    fun onChecked(itemTitle: Int, state: Boolean) {
        Log.d(TAG, "onChecked")
    }

    fun onNavigate(itemTitle: Int) {
        Log.d(TAG, "onNavigate")
    }

    fun onNavigate(itemListModel: ItemListModel){
        Log.d(TAG, "onNavigate Menu Item")
    }

    fun onBackStack() {
        Log.d(TAG, "onBackStack")
    }

    fun onShowDialog(){
        Log.d(TAG, "onShowDialog")
    }
    companion object {
        const val TAG = "CommonItemMenuAction"
    }
}