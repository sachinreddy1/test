package com.example.recyclerviewtest.presentation.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("android:items")
fun setItems(recyclerView: RecyclerView, items: List<ListItem>) {
    if (recyclerView.adapter is RecyclerViewAdapter<*>) {
        (recyclerView.adapter as RecyclerViewAdapter<ListItem>).itemList = items
    }
}

@BindingAdapter("android:timelineItems")
fun setTimelineItems(recyclerView: com.example.timelineview.RecyclerView, items: List<ListItem>) {
    if (recyclerView.adapter is BottomRecyclerViewAdapter<*>) {
        (recyclerView.adapter as BottomRecyclerViewAdapter<ListItem>).itemList = items
    }
}

@BindingAdapter("android:integer")
fun setInteger(textView: TextView, value: Int) {
    textView.text = value.toString()
}