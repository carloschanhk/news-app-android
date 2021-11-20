package com.carloscoding.newsapp.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "article_entity")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String?,
    val source: String?,
    val author: String?,
    val content: String?,
    val description: String?,
    @ColumnInfo(name = "publish_at")
    val publishedAt: Date?,
    val title: String?,
    val url: String?,
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String?
)