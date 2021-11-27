package com.carloscoding.newsapp.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.carloscoding.newsapp.api.NewsApi
import com.carloscoding.newsapp.local.database.ArticleDao
import com.carloscoding.newsapp.local.database.ArticleDatabase
import com.carloscoding.newsapp.local.database.ArticleDatabase.Companion.DATABASE_NAME
import com.carloscoding.newsapp.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val SETTING_PREF = "setting_pref"
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

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create { context.preferencesDataStoreFile(SETTING_PREF) }
    }
}