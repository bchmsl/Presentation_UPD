package com.bchmsl.presentation_upd

data class User(
    val name: String,
    val email: String
) {
    var id: Int = lastId

    init {
        lastId++
    }
}

var lastId = 1
