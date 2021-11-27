package com.carloscoding.newsapp.domain.setting

import com.carloscoding.newsapp.repository.SettingRepository
import javax.inject.Inject

class SetPreferencesUseCase @Inject constructor(val repository: SettingRepository) {
    suspend operator fun invoke(selectedCategories: List<String>){
        repository.setPreferences(selectedCategories)
    }
}