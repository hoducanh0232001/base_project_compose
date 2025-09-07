package com.example.data.http.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompletionMapData(
    @SerialName("items")
    val items: List<String>,
    @SerialName("total")
    val total: Int,
)
