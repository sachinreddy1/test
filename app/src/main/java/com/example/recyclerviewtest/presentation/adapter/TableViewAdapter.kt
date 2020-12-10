package com.example.recyclerviewtest.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.example.recyclerviewtest.R
import com.example.recyclerviewtest.presentation.table.data.Cell
import com.example.recyclerviewtest.presentation.table.data.ColumnHeader
import com.example.recyclerviewtest.presentation.table.data.RowHeader
import com.example.recyclerviewtest.presentation.table.view.CellViewHolder
import com.example.recyclerviewtest.presentation.table.view.ColumnHeaderViewHolder
import com.example.recyclerviewtest.presentation.table.view.RowHeaderViewHolder
import javax.inject.Inject

class TableViewAdapter @Inject constructor(
    val context: Context
) : AbstractTableAdapter<ColumnHeader?, RowHeader?, Cell?>() {

    var cells : List<List<Cell>> = listOf()
        set(value) {
            val diff = DiffUtil.calculateDiff(
                CellDiffCallback(
                    cells,
                    value
                ), true)
            field = value
            diff.dispatchUpdatesTo(cellRecyclerViewAdapter)
        }

    var rowHeaders : List<RowHeader> = listOf()
        set(value) {
            val diff = DiffUtil.calculateDiff(
                RowHeaderDiffCallback(
                    rowHeaders,
                    value
                ), true)
            field = value
            diff.dispatchUpdatesTo(rowHeaderRecyclerViewAdapter)
        }

    var columnHeaders : List<ColumnHeader> = listOf()
        set(value) {
            val diff = DiffUtil.calculateDiff(
                ColumnHeaderDiffCallback(
                    columnHeaders,
                    value
                ), true)
            field = value
            diff.dispatchUpdatesTo(columnHeaderRecyclerViewAdapter)
        }

    class CellDiffCallback(val old: List<List<Cell>>, val updated: List<List<Cell>>): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition].containsAll(updated[newItemPosition])
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = updated.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition] == updated[newItemPosition]
    }

    class RowHeaderDiffCallback(val old: List<RowHeader>, val updated: List<RowHeader>): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition] == updated[newItemPosition]
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = updated.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition] == updated[newItemPosition]
    }

    class ColumnHeaderDiffCallback(val old: List<ColumnHeader>, val updated: List<ColumnHeader>): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition] == updated[newItemPosition]
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = updated.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition] == updated[newItemPosition]
    }

    override fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder =
        CellViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.table_view_cell_layout, parent, false)
        )

    override fun onBindCellViewHolder(
        holder: AbstractViewHolder,
        cellItemModel: Cell?,
        columnPosition: Int,
        rowPosition: Int
    ) {
        (holder as CellViewHolder).cell = cells[rowPosition][columnPosition]
    }

    override fun onCreateColumnHeaderViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder = ColumnHeaderViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.table_view_column_header_layout, parent, false)
    )

    override fun onBindColumnHeaderViewHolder(
        holder: AbstractViewHolder,
        timelineHeaderItemModel: ColumnHeader?,
        columnPosition: Int
    ) {
        (holder as ColumnHeaderViewHolder).columnHeader = columnHeaders[columnPosition]
    }

    override fun onCreateRowHeaderViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder = RowHeaderViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.table_view_row_header_layout, parent, false)
    )

    override fun onBindRowHeaderViewHolder(
        holder: AbstractViewHolder,
        rowHeaderItemModel: RowHeader?,
        rowPosition: Int
    ) {
        (holder as RowHeaderViewHolder).rowHeader = rowHeaders[rowPosition]
    }

    override fun onCreateCornerView(parent: ViewGroup): View = LayoutInflater.from(parent.context)
        .inflate(R.layout.table_view_corner_layout, parent, false)

    override fun getColumnHeaderItemViewType(columnPosition: Int): Int = 0

    override fun getRowHeaderItemViewType(rowPosition: Int): Int = 0

    override fun getCellItemViewType(columnPosition: Int): Int = 0

}