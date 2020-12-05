package com.example.recyclerviewtest.presentation.adapter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.recyclerviewtest.BR

class TestObservable : BaseObservable() {
    private var value: String = "0"

    @Bindable
    fun getValue(): String = value

    fun setValue(value: String) {
        this.value = value
        notifyPropertyChanged(BR.value)
    }
}