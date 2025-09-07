package com.example.data.http.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreSignURLResponse(
    @SerialName("url")
    val url: String,
    @SerialName("action")
    val action: String,
)
