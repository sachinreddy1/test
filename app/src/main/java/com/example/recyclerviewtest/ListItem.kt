package com.example.recyclerviewtest

data class ListItem(
    override val id: String,
    val text: String
) : Identifiable {

}