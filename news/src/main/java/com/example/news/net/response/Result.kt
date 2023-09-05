package com.example.news.net.response

@kotlinx.serialization.Serializable
data class Result(
    val id: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val fields: Fields?=null,
)