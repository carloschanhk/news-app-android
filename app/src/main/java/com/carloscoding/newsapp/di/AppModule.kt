package com.carloscoding.newsapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.carloscoding.newsapp.api.NewsApi
import com.carloscoding.newsapp.database.ArticleDao
import com.carloscoding.newsapp.database.ArticleDatabase
import com.carloscoding.newsapp.database.ArticleDatabase.Companion.DATABASE_NAME
import com.carloscoding.newsapp.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNewsApi() : NewsApi =
        Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideArticleRoomDatabase(app: Application) : ArticleDatabase {
        return Room.databaseBuilder(
            app,
            ArticleDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao = articleDatabase.articleDao()
}