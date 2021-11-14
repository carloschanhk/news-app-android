package com.carloscoding.newsapp.home.ui

import com.carloscoding.newsapp.data.Article

data class HomeState(
    val articlesPresented: List<Article> = listOf(),
    val selectedCategory: String = "Today",
    val errorMessage: String? = null,
    val isLoading: Boolean = true,
)
