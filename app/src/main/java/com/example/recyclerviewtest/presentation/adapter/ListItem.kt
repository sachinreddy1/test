package com.example.recyclerviewtest.presentation.adapter

import com.example.recyclerviewtest.Identifiable

data class ListItem(
    override val id: String,
    val text: String
) : Identifiable {

}