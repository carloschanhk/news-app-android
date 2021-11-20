package com.carloscoding.newsapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.database.ArticleEntity
import com.carloscoding.newsapp.home.remote.dto.ArticleDto
import com.carloscoding.newsapp.utils.Mapper.mapToDate
import java.text.SimpleDateFormat
import java.util.*

object Mapper {
    fun ArticleDto.mapToData(category: String?): Article {
        return Article(
            category = category,
            source = source?.name,
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt?.mapToDate(),
            title = title,
            url = url,
            urlToImage = urlToImage,
        )
    }

    private fun String.mapToDate(): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.CHINESE)
        return dateFormat.parse(this)
    }

    fun Article.mapToLocal(): ArticleEntity {
        return ArticleEntity(
            category = category,
            source = source,
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            title = title,
            url = url,
            urlToImage = urlToImage,
        )
    }

    fun ArticleEntity.mapToData(): Article {
        return Article(
            category = category,
            source = source,
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            title = title,
            url = url,
            urlToImage = urlToImage,
        )
    }
}