package com.example.recyclerviewtest.presentation.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timelineview.TimelineView

@BindingAdapter("android:timelineItems")
fun setTimelineItems(timelineView: TimelineView, items: List<ListItem>) {
    if (timelineView.adapter is BottomRecyclerViewAdapter<*>) {
        (timelineView.adapter as BottomRecyclerViewAdapter<ListItem>).itemList = items
    }
}

@BindingAdapter("android:integer")
fun setInteger(textView: TextView, value: Int) {
    textView.text = value.toString()
}