package com.carloscoding.newsapp.home.repository

import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.home.remote.NewsRemoteDatasource
import com.carloscoding.newsapp.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val datasource: NewsRemoteDatasource
) {
    private var cacheArticles: List<Article>? = null

    suspend fun getTopHeadlines(categories: List<String>): Result<List<Article>> {
        return try {
            val newArticles = mutableListOf<Article>()
            for (category in categories) {
                newArticles.addAll(datasource.getHeadlinesByCategory(category))
            }
            Result.Success(newArticles)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getHeadlinesByCategory(category: String): Result<List<Article>> {
        return try {
            Result.Success(datasource.getHeadlinesByCategory(category))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun searchHeadlines(keyword: String): Result<List<Article>> {
        return try {
            Result.Success(datasource.searchHeadlines(keyword))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun getCachedArticles(): List<Article>? = cacheArticles

    fun setCache(articles: List<Article>) {
        cacheArticles = articles
    }
}