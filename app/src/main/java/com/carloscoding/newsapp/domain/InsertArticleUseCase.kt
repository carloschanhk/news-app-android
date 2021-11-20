package com.carloscoding.newsapp.domain

import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.repository.FavouriteRepository
import javax.inject.Inject

class InsertArticleUseCase @Inject constructor(val repository: FavouriteRepository) {
    suspend operator fun invoke(article: Article) {
        repository.insertArticle(article)
    }
}