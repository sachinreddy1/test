package com.example.recyclerviewtest.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.R

class RecyclerViewAdapter(private val context: Context, size: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<Int> = List(size) { it }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_timeline, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as ItemViewHolder).setItemDetails(items[position])

    internal inner class ItemViewHolder(artistView: View) : RecyclerView.ViewHolder(artistView) {
        fun setItemDetails(value: Int) {

        }
    }
}