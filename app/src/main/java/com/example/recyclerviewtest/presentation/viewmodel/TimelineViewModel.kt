package com.example.recyclerviewtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.recyclerviewtest.presentation.adapter.ListItem
import com.example.timelineview.RecyclerView
import javax.inject.Inject

class TimelineViewModel @Inject constructor() : ViewModel() {
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