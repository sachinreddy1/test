package com.example.recyclerviewtest.presentation.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @BindingAdapter("time")
    @JvmStatic fun setTime(view: TextView, newValue: String) {
        // Important to break potential infinite loops.
        if (view.text != newValue) {
            view.text = newValue
        }
    }

}