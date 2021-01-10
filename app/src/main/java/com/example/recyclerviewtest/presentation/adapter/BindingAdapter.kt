package com.example.recyclerviewtest.presentation.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.timelineview.RecyclerView

@BindingAdapter("android:timelineItems")
fun setTimelineItems(timelineView: RecyclerView, items: List<ListItem>) {
    if (timelineView.adapter is BottomRecyclerViewAdapter<*>) {
        (timelineView.adapter as BottomRecyclerViewAdapter<ListItem>).itemList = items
    }
}

@BindingAdapter("android:integer")
fun setInteger(textView: TextView, value: Int) {
    textView.text = value.toString()
}