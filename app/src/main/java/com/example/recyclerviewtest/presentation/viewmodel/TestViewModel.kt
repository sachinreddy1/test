package com.example.recyclerviewtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.recyclerviewtest.presentation.adapter.ListItem
import javax.inject.Inject
import javax.inject.Singleton

class TestViewModel @Inject constructor() : ViewModel() {
    val testValue = "Hello World"
}