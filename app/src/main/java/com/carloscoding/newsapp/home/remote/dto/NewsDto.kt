package com.carloscoding.newsapp.home.remote.dto

data class NewsDto(
    val status: String,
    val totalResult: Int,
    val articles: List<ArticleDto>,
)
