package com.carloscoding.newsapp.remote

import com.carloscoding.newsapp.api.NewsApi
import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.utils.Mapper.mapToData
import javax.inject.Inject

class NewsRemoteDatasource @Inject constructor(
    private val newsApi: NewsApi,
) {
    suspend fun getHeadlinesByCategory(category: String): List<Article>{
        return newsApi.getHeadlinesByCategory(category.lowercase()).articles.map { it.mapToData(category) }
    }

    suspend fun searchHeadlines(keyword: String): List<Article> {
        return newsApi.searchHeadlines(keyword).articles.map { it.mapToData(null) }
    }
}