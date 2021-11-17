package com.carloscoding.newsapp.data

import java.io.Serializable
import java.util.*

data class Article(
    val category: String?,
    val source: String?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: Date?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable
