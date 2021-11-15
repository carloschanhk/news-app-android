package com.carloscoding.newsapp.common_ui.news

import com.carloscoding.newsapp.data.Article

data class NewsState(
    val articlesPresented: List<Article> = listOf(),
    val errorMessage: String? = null,
    val isLoading: Boolean = true,
)
