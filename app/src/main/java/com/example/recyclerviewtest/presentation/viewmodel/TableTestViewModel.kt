package com.example.recyclerviewtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerviewtest.presentation.adapter.ListItem
import com.example.recyclerviewtest.presentation.table.data.Cell
import com.example.recyclerviewtest.presentation.table.data.ColumnHeader
import com.example.recyclerviewtest.presentation.table.data.RowHeader
import javax.inject.Inject

class TableTestViewModel @Inject constructor() : ViewModel() {
    var testValue: MutableLiveData<String> = MutableLiveData("0")

    var testList: MutableLiveData<List<ListItem>> = MutableLiveData(
        listOf(
            ListItem("0", "0"),
            ListItem("1", "1"),
            ListItem("2", "2"),
            ListItem("3", "3")
        )
    )

    var cells: MutableLiveData<List<List<Cell>>> = MutableLiveData(
        listOf(
            listOf(
                Cell("0"),
                Cell("1"),
                Cell("2"),
                Cell("3"),
                Cell("4"),
                Cell("5"),
                Cell("6"),
                Cell("7")
            )
        )
    )

    var rowHeaders: MutableLiveData<List<RowHeader>> = MutableLiveData(
        listOf(
            RowHeader("0")
        )
    )

    var columnHeaders: MutableLiveData<List<ColumnHeader>> = MutableLiveData(
        listOf(
            ColumnHeader("0"),
            ColumnHeader("1"),
            ColumnHeader("2"),
            ColumnHeader("3"),
            ColumnHeader("4"),
            ColumnHeader("5"),
            ColumnHeader("6"),
            ColumnHeader("7")
        )
    )
}