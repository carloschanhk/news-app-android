package com.carloscoding.newsapp.common_ui.news

import com.carloscoding.newsapp.data.Article

sealed class NewsFragEvent{
    object  OnRefresh: NewsFragEvent()
    data class OnArticleClicked(val article: Article): NewsFragEvent()
}
