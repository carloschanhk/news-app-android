package com.carloscoding.newsapp.home.usecase

import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.home.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(categories: List<String>, isRefresh: Boolean): ArticleOutput {
        return withContext(Dispatchers.IO) {
            val cache = repository.getCachedArticles()
            if (!isRefresh) {
                cache?.let {
                    var isContainAllCategories = true
                    for (category in categories) {
                        if (!it.any { article -> article.category == category }) {
                            isContainAllCategories = false
                            break
                        }
                    }
                    if (isContainAllCategories) {
                        return@withContext ArticleOutput.Success(it.sortedByDescending { article -> article.publishedAt })
                    }
                }
            }
            // Get new data when refresh required or cache is null
            val result = repository.getTopHeadlines(categories)
            return@withContext result.takeIfSuccess()?.let {
                repository.setCache(it)
                ArticleOutput.Success(it.sortedByDescending { article -> article.publishedAt })
            } ?: ArticleOutput.Error(result.takeError())
        }
    }
}