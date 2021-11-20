package com.carloscoding.newsapp.remote.dto

data class ArticleDto(
    val source: SourceDto?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)
