package com.example.recyclerviewtest.presentation.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evrencoskun.tableview.TableView
import com.example.recyclerviewtest.presentation.table.data.Cell
import com.example.recyclerviewtest.presentation.table.data.ColumnHeader
import com.example.recyclerviewtest.presentation.table.data.RowHeader

@BindingAdapter("android:items")
fun setItems(recyclerView: RecyclerView, items: List<ListItem>) {
    if (recyclerView.adapter is RecyclerViewAdapter<*>) {
        (recyclerView.adapter as RecyclerViewAdapter<ListItem>).itemList = items
    }
}

@BindingAdapter("android:cells")
fun setCells(tableView: TableView, cells: List<List<Cell>>) {
    if (tableView.adapter is TableViewAdapter) {
        (tableView.adapter as TableViewAdapter).let {
            it.setCellItems(cells)
            it.cells = cells
        }
    }
}

@BindingAdapter("android:rowHeaders")
fun setRowHeaders(tableView: TableView, rowHeaders: List<RowHeader>) {
    if (tableView.adapter is TableViewAdapter) {
        (tableView.adapter as TableViewAdapter).let {
            it.setRowHeaderItems(rowHeaders)
            it.rowHeaders = rowHeaders
        }
    }
}

@BindingAdapter("android:columnHeaders")
fun setColumnHeaders(tableView: TableView, columnHeaders: List<ColumnHeader>) {
    if (tableView.adapter is TableViewAdapter) {
        (tableView.adapter as TableViewAdapter).let {
            it.setColumnHeaderItems(columnHeaders)
            it.columnHeaders = columnHeaders
        }
    }
}