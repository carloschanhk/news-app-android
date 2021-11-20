package com.carloscoding.newsapp.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ArticleDao {
    @Insert
    suspend fun insert(articleEntity: ArticleEntity)

    @Query("SELECT * FROM article_entity")
    fun getArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT EXISTS (SELECT * FROM article_entity WHERE publish_at =:publishAt AND title = :title)")
    suspend fun isArticleBookmarked(publishAt: Date?, title: String?): Boolean

    @Query("DELETE FROM article_entity WHERE publish_at =:publishAt AND title = :title")
    suspend fun deleteArticle(publishAt: Date?, title: String?)
}