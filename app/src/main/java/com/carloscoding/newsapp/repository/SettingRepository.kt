package com.carloscoding.newsapp.repository

import com.carloscoding.newsapp.local.SettingPreferenceDatasource
import com.carloscoding.newsapp.utils.Result
import java.lang.Exception
import javax.inject.Inject

class SettingRepository @Inject constructor(private val datasource: SettingPreferenceDatasource) {
    suspend fun setPreferences(selectedCategories: List<String>) {
        datasource.setPreferences(selectedCategories)
    }

    suspend fun getPreferences(): Result<List<String>> {
        return try {
            Result.Success(datasource.getPreferences())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}