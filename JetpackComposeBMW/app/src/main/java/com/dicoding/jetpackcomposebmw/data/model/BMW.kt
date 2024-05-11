package com.dicoding.jetpackcomposebmw.data.model

import java.io.Serializable

data class BMW(
    val id: String,
    val name: String,
    val description: String,
    val color: String,
    val type: String,
    val photoId: Int = 0
) : Serializable