package com.example.recyclerviewtest.presentation.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("android:items")
fun setItems(recyclerView: RecyclerView, items: List<ListItem>) {
    if (recyclerView.adapter is RecyclerViewAdapter<*>) {
        (recyclerView.adapter as RecyclerViewAdapter<ListItem>).itemList = items
    }
}