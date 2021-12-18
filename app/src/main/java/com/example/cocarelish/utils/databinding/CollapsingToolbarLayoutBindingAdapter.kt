package com.example.cocarelish.utils.databinding

import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout

@BindingAdapter("android:background")
fun CollapsingToolbarLayout.background(resource: Int) {
    if (resource > 0)
        background = ResourcesCompat.getDrawable(context.resources, resource, context.theme)
}

@BindingAdapter("app:title")
fun CollapsingToolbarLayout.title(titleID: Int) {
    if (titleID > 0)
        title = resources.getString(titleID)
}

@BindingAdapter("app:text")
fun CollapsingToolbarLayout.text(textString: String?) {
    textString?.let { title = it }
}