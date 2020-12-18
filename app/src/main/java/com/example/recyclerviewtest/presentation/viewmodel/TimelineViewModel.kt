package com.example.recyclerviewtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerviewtest.presentation.adapter.ListItem
import com.example.recyclerviewtest.presentation.table.data.Cell
import com.example.recyclerviewtest.presentation.table.data.ColumnHeader
import com.example.recyclerviewtest.presentation.table.data.RowHeader
import javax.inject.Inject

class TimelineViewModel @Inject constructor() : ViewModel() {
    var testValue: MutableLiveData<String> = MutableLiveData("0")
}