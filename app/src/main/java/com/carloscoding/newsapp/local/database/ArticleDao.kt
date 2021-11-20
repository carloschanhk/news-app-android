package com.carloscoding.newsapp.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert
    suspend fun insert(articleEntity: ArticleEntity)

    @Query("SELECT * FROM article_entity")
    suspend fun getArticles(): Flow<List<ArticleEntity>>
}