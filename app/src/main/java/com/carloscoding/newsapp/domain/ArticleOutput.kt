package com.carloscoding.newsapp.domain

import com.carloscoding.newsapp.data.Article
import java.lang.Exception

sealed class ArticleOutput {
    data class Success(
        val articles: List<Article>
    ) : ArticleOutput()

    data class Error(
        val exception: Exception
    ) : ArticleOutput()
}
