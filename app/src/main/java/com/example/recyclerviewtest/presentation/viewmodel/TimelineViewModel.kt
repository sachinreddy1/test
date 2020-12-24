package com.example.recyclerviewtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerviewtest.presentation.adapter.ListItem
import com.example.recyclerviewtest.presentation.table.data.Cell
import com.example.recyclerviewtest.presentation.table.data.ColumnHeader
import com.example.recyclerviewtest.presentation.table.data.RowHeader
import javax.inject.Inject

class TimelineViewModel @Inject constructor() : ViewModel() {
    var topList: List<ListItem> = listOf(
        ListItem("0", "0"),
        ListItem("1", "1"),
        ListItem("2", "2"),
        ListItem("3", "3"),
        ListItem("4", "4"),
        ListItem("5", "5"),
        ListItem("6", "6"),
        ListItem("7", "7"),
        ListItem("8", "8"),
        ListItem("9", "9"),
        ListItem("10", "10"),
        ListItem("11", "11")
    )

    var bottomList: List<ListItem> = listOf(
        ListItem("0", "0"),
        ListItem("1", "1"),
        ListItem("2", "2"),
        ListItem("3", "3"),
        ListItem("4", "4"),
        ListItem("5", "5"),
        ListItem("6", "6"),
        ListItem("7", "7"),
        ListItem("8", "8")
    )
}