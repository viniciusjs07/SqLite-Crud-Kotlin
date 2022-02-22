package com.example.sqlite_crud.model

import java.util.*

data class CentralModel(
    var id: Int = getAutoId(),
    var name: String = "",
    var model: String = ""
) {
    companion object {
        fun getAutoId(): Int {
            return Random().nextInt(100)
        }
    }
}