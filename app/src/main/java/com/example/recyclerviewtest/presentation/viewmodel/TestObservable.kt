package com.example.recyclerviewtest.presentation.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.recyclerviewtest.BR
import com.example.recyclerviewtest.presentation.adapter.ListItem

class TestObservable : BaseObservable() {
    @get:Bindable
    var value: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.value)
        }

    @get:Bindable
    var list: List<ListItem> = listOf(
        ListItem("0", "0"),
        ListItem("1", "1"),
        ListItem("2", "2"),
        ListItem("3", "3"),
        ListItem("4", "4"),
        ListItem("5", "5"),
        ListItem("6", "6"),
        ListItem("7", "7")
    )
        set(list) {
            field = list
            notifyPropertyChanged(BR.list)
        }
}