package com.carloscoding.newsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert
    fun insert(articleEntity: ArticleEntity)

    @Query("SELECT * FROM article_entity")
    fun getAll(): Flow<List<ArticleEntity>>
}