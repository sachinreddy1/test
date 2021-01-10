package com.example.recyclerviewtest.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.timelineview.DiffUtil
import com.example.timelineview.RecyclerView
import com.example.recyclerviewtest.IRecyclerViewAdapter
import com.example.recyclerviewtest.Identifiable

class BottomRecyclerViewAdapter<T>(private val viewTypeProvider: ViewTypeProvider<T>,
                             private val itemResId: Int) : RecyclerView.Adapter<BottomRecyclerViewAdapter.BottomCustomViewHolder<T>>(),
    IRecyclerViewAdapter<T>
where T : Identifiable {

    override var itemList : List<T> = listOf()
        set(value) {
            val diff = DiffUtil.calculateDiff(
                ListDiffCallback(
                    itemList,
                    value
                ), true)
            field = value
            diff.dispatchUpdatesTo(this)
        }

    class ListDiffCallback<T>(val old: List<T>, val updated: List<T>): DiffUtil.Callback() where T : Identifiable {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition].isEquivalent(updated[newItemPosition])
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = updated.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition] == updated[newItemPosition]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomCustomViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BottomCustomViewHolder(
            view,
            itemResId
        )
    }

    override fun onBindViewHolder(holder: BottomCustomViewHolder<T>, position: Int) {
        holder.item = itemList[position]
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int = viewTypeProvider(itemList[position])

    class BottomCustomViewHolder<T>(itemView: View, private val itemResId: Int) : RecyclerView.ViewHolder(itemView) {
        private var _item: T? = null
        var item: T?
            set(value) {
                _item = value
                binding?.setVariable(itemResId, value)
                binding?.executePendingBindings()
            }
            get() = _item

        val binding: ViewDataBinding? = try { DataBindingUtil.bind(itemView) } catch (t: Throwable) { null }
    }
}

typealias  ViewTypeProvider<T> = (T) -> Int