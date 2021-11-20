package com.carloscoding.newsapp.remote.dto

data class NewsDto(
    val status: String,
    val totalResult: Int,
    val articles: List<ArticleDto>,
)
