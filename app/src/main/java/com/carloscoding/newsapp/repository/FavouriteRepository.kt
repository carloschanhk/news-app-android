package com.carloscoding.newsapp.repository

import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.local.FavouriteArticleDatasource
import com.carloscoding.newsapp.utils.Result
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

class FavouriteRepository @Inject constructor(
    private val datasource: FavouriteArticleDatasource
) {
    suspend fun insertArticle(article: Article) {
        datasource.insertArticle(article)
    }

    suspend fun getArticles(): Result<Flow<List<Article>>> {
        return try {
            Result.Success(datasource.getArticles())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun isArticleBookmarked(article: Article): Result<Boolean>{
        return try {
            Result.Success(datasource.isArticleBookmarked(article))
        } catch (e:Exception){
            Result.Error(e)
        }
    }

    suspend fun deleteArticle(article: Article){
        datasource.deleteArticle(article)
    }
}