package com.carloscoding.newsapp.home.usecase

import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.home.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
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