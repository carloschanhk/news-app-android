package com.carloscoding.newsapp.home.ui

import com.carloscoding.newsapp.data.Article

sealed class HomePageEvent {
    data class OnToggleCategory(val category: String) : HomePageEvent()
    data class OnArticleClicked(val article: Article) : HomePageEvent()
    data class OnRefreshCategory(val category: String): HomePageEvent()
}
