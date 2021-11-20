package com.carloscoding.newsapp.domain.favourite

import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.repository.FavouriteRepository
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(val repository: FavouriteRepository) {
    suspend operator fun invoke(article:Article){
        repository.deleteArticle(article)
    }
}