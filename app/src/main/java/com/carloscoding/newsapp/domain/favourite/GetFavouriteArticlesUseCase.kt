package com.carloscoding.newsapp.domain.favourite

import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.repository.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GetFavouriteArticlesUseCase @Inject constructor(val repository: FavouriteRepository) {
    suspend operator fun invoke(): Output {
        return withContext(Dispatchers.IO){
            val result = repository.getArticles()
            result.takeIfSuccess()?.let {
                return@withContext Output.Success(it)
            } ?: Output.Error(result.takeError())
        }
    }

    sealed class Output {
        data class Success(
            val articlesFlow : Flow<List<Article>>
        ) : Output()
        data class Error(
            val exception : Exception
        ) : Output()
    }
}