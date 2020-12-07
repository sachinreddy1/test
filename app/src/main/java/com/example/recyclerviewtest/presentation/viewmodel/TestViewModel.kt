package com.example.recyclerviewtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TestViewModel @Inject constructor() : ViewModel() {
    var testValue: TestObservable =
        TestObservable()
}