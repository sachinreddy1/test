package com.example.recyclerviewtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerviewtest.presentation.adapter.ListItem
import javax.inject.Inject

class TestViewModel @Inject constructor() : ViewModel() {
    var testValue: MutableLiveData<String> = MutableLiveData("0")

    var testList: MutableLiveData<List<ListItem>> = MutableLiveData(
        listOf(
            ListItem("0", "0"),
            ListItem("1", "1"),
            ListItem("2", "2"),
            ListItem("3", "3")
        )
    )
}