package com.example.cocarelish.utils.databinding

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cocarelish.utils.Consts
import com.example.cocarelish.utils.Status
import org.w3c.dom.Text

@BindingAdapter("android:visibility")
fun setVisibility(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter(" android:layout_height ")
fun Button.setLayoutHeight(isVisible: Boolean) {
    val layoutParams: ViewGroup.LayoutParams = layoutParams
    if (isVisible) layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
    else layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    this.layoutParams = layoutParams
}

@BindingAdapter(value = ["position", "type_topic"], requireAll = false)
fun TextView.setTextTopic(position: Int, typeTopic: String) {
    val textType = Consts.HASH_TAG + position + Consts.COLON + Consts.SPACE + typeTopic
    this.text = textType
}


@BindingAdapter(value = ["status_id", "score"])
fun TextView.setTextStatus(status_id: Int, score: String?) {
    Log.e("TextView", "setTextStatus: $status_id with $score", )
    if (status_id == Status.STATUS_1) {
        this.text = Consts.SCORE + score
    } else if (status_id == Status.STATUS_2) {
        this.text = Consts.SENT
    }
}