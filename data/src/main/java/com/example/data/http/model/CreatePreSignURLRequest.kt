package com.example.data.http.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePreSignURLRequest(
    @SerialName("fileName")
    val fileName: String,
    @SerialName("fileType")
    val fileType: String,
    @SerialName("action")
    val action: String,
)
