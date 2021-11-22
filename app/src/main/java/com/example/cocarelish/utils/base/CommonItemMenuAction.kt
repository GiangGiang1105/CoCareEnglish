package com.example.cocarelish.utils.base

import android.util.Log

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

    fun onBackStack() {
        Log.d(TAG, "onBackStack")
    }
    companion object {
        const val TAG = "CommonItemMenuAction"
    }
}