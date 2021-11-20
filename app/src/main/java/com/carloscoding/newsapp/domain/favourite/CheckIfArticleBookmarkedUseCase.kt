package com.carloscoding.newsapp.domain.favourite

import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.repository.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class CheckIfArticleBookmarkedUseCase @Inject constructor(val repository: FavouriteRepository) {
    suspend operator fun invoke(article: Article): Output {
        return withContext(Dispatchers.IO) {
            val result = repository.isArticleBookmarked(article)
            result.takeIfSuccess()?.let {
                Output.Success(it)
            } ?: Output.Error(result.takeError())
        }
    }

    sealed class Output {
        data class Success(
            val isBookmarked: Boolean
        ) : Output()

        data class Error(
            val exception: Exception
        ) : Output()
    }
}