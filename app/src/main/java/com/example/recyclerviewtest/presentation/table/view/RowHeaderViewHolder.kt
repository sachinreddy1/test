package com.example.recyclerviewtest.presentation.table.view

import android.view.View
import androidx.databinding.DataBindingUtil
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.example.recyclerviewtest.databinding.TableViewColumnHeaderLayoutBinding
import com.example.recyclerviewtest.databinding.TableViewRowHeaderLayoutBinding
import com.example.recyclerviewtest.presentation.table.data.ColumnHeader
import com.example.recyclerviewtest.presentation.table.data.RowHeader

class RowHeaderViewHolder(
    itemView: View
) : AbstractViewHolder(itemView) {
    private var _rowHeader: RowHeader? = null
    val binding: TableViewRowHeaderLayoutBinding? = try { DataBindingUtil.bind(itemView) } catch (t: Throwable) { null }

    var rowHeader: RowHeader?
        set(value) {
            _rowHeader = value
            binding?.rowHeader = value
            binding?.executePendingBindings()
        }
        get() = _rowHeader
}