package com.carloscoding.newsapp.api

import com.carloscoding.newsapp.remote.dto.NewsDto
import com.carloscoding.newsapp.utils.Constant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country: String = "us",
        @Query("page")
        page: Int = 2,
        @Query("apiKey")
        key: String = API_KEY,
    ) : NewsDto

    @GET("top-headlines")
    suspend fun getHeadlinesByCategory(
        @Query("category")
        category: String,
        @Query("country")
        country: String = "us",
        @Query("page")
        page: Int = 2,
        @Query("apiKey")
        key: String = API_KEY,
    ) : NewsDto

    @GET("everything")
    suspend fun searchHeadlines(
        @Query("q")
        keyword: String,
        @Query("country")
        country: String = "us",
        @Query("language")
        language: String = "en",
        @Query("apiKey")
        key: String = API_KEY,
    ): NewsDto
}