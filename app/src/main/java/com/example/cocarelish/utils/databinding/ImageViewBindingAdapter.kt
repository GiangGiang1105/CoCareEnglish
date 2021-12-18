package com.example.cocarelish.utils.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:url")
fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}