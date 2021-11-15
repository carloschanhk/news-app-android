package com.carloscoding.newsapp.home.usecase

import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.home.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GetHeadlinesByCategoryUseCase @Inject constructor(
    private val repository: NewsRepository,
) {
    suspend operator fun invoke(category: String, isRefresh: Boolean): ArticleOutput {
        return withContext(Dispatchers.IO) {
            val cache = repository.getCachedArticles()?.toMutableList() ?: mutableListOf()
            val relatedArticles = cache.filter { it.category == category }
            if (!isRefresh && relatedArticles.isNotEmpty()) {
                return@withContext ArticleOutput.Success(relatedArticles.sortedByDescending { article -> article.publishedAt })
            }
            val result = repository.getHeadlinesByCategory(category)
            return@withContext result.takeIfSuccess()?.let {
                cache.removeAll { article -> article.category == category }
                cache.addAll(it)
                repository.setCache(cache)
                ArticleOutput.Success(it.sortedByDescending { article -> article.publishedAt })
            } ?: ArticleOutput.Error(result.takeError())
        }

    }

}