package com.example.recyclerviewtest.presentation.adapter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.recyclerviewtest.BR

class TestObservable : BaseObservable() {

    private var value: String = "hello"

    @Bindable
    fun getValue(): String = this.value

    fun setValue(value: String) {
        this.value = value
        notifyPropertyChanged(BR.value)
    }

    private var list: List<ListItem> = listOf(
        ListItem("0", "0"),
        ListItem("1", "1"),
        ListItem("2", "2"),
        ListItem("3", "3"),
        ListItem("4", "4"),
        ListItem("5", "5"),
        ListItem("6", "6"),
        ListItem("7", "7")
    )

    @Bindable
    fun getList(): List<ListItem> = this.list

    fun setList(list: List<ListItem>) {
        this.list = list
        notifyPropertyChanged(BR.list)
    }

}