package com.example.recyclerviewtest.presentation.table.view

import android.view.View
import androidx.databinding.DataBindingUtil
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.example.recyclerviewtest.databinding.TableViewColumnHeaderLayoutBinding
import com.evrencoskun.tableview.data.ColumnHeader

class ColumnHeaderViewHolder(
    itemView: View
) : AbstractViewHolder(itemView) {
    private var _columnHeader: ColumnHeader? = null
    val binding: TableViewColumnHeaderLayoutBinding? = try { DataBindingUtil.bind(itemView) } catch (t: Throwable) { null }

    var columnHeader: ColumnHeader?
        set(value) {
            _columnHeader = value
            binding?.columnHeader = value
            binding?.executePendingBindings()
        }
        get() = _columnHeader
}