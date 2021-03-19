package com.example.recyclerviewtest.presentation.table.view

import android.view.View
import androidx.databinding.DataBindingUtil
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.example.recyclerviewtest.databinding.TableViewCellLayoutBinding
import com.evrencoskun.tableview.data.Cell

class CellViewHolder(
    itemView: View
) : AbstractViewHolder(itemView) {
    private var _cell: Cell? = null
    val binding: TableViewCellLayoutBinding? = try { DataBindingUtil.bind(itemView) } catch (t: Throwable) { null }

    var cell: Cell?
        set(value) {
            _cell = value
            binding?.cell = value
            binding?.executePendingBindings()
        }
        get() = _cell
}