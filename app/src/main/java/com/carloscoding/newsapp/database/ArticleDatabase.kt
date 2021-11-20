package com.carloscoding.newsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [ArticleEntity::class], version = 1)
@TypeConverters(RoomTypeConverter::class)
abstract class ArticleDatabase : RoomDatabase(){
    abstract fun articleDao(): ArticleDao

    companion object{
        const val DATABASE_NAME = "article_db"
    }
}