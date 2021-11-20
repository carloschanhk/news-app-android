package com.carloscoding.newsapp.domain.remote

import com.carloscoding.newsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchHeadlinesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(keyword: String): ArticleOutput {
        return withContext(Dispatchers.IO) {
            val result = repository.searchHeadlines(keyword)
            return@withContext result.takeIfSuccess()?.let {
                ArticleOutput.Success(it)
            } ?: ArticleOutput.Error(result.takeError())
        }
    }
}