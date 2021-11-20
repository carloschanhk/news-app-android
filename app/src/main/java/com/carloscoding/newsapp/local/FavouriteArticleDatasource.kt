package com.carloscoding.newsapp.local

import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.local.database.ArticleDao
import com.carloscoding.newsapp.utils.Mapper.mapToData
import com.carloscoding.newsapp.utils.Mapper.mapToLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteArticleDatasource @Inject constructor(
    private val articleDao: ArticleDao
) {
    suspend fun insertArticle(article: Article){
        articleDao.insert(articleEntity = article.mapToLocal())
    }

    suspend fun getArticles() : Flow<List<Article>>{
        return articleDao.getArticles().map { articles -> articles.map { it.mapToData() } }
    }
}