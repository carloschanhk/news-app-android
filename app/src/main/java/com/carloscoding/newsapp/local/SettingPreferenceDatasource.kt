package com.carloscoding.newsapp.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.carloscoding.newsapp.utils.Constant.CONST_CATEGORIES
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SettingPreferenceDatasource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val CATEGORY_PREF_KEY = stringPreferencesKey("category_pref")
        val gson = Gson()
    }

    suspend fun setPreferences(selectedCategories: List<String>) {
        dataStore.edit { store ->
            store[CATEGORY_PREF_KEY] = gson.toJson(selectedCategories)
        }
    }

    suspend fun getPreferences(): List<String>{
        return dataStore.data.first()[CATEGORY_PREF_KEY]?.let{
            gson.fromJson(it,CategoryPref::class.java).pref
        } ?: CONST_CATEGORIES
    }

    data class CategoryPref(
        val pref : List<String>
    )
}