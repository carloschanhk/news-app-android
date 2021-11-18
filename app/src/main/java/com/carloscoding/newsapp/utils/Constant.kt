package com.carloscoding.newsapp.utils

import com.carloscoding.newsapp.BuildConfig
import com.carloscoding.newsapp.R

object Constant {
    const val BASE_URL = "https://newsapi.org/v2/"
    // Add your API_KEY to your local.properties
    const val API_KEY = BuildConfig.API_KEY

    val ColorMap : Map<String,Int> = mapOf(
        "Business" to R.color.teal_200,
        "Entertainment" to R.color.red,
        "Health" to R.color.green,
        "Science" to R.color.pink,
        "Sports" to R.color.color_main,
        "Technology" to R.color.orange,
    )
}