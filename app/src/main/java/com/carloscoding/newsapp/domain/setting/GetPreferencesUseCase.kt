package com.carloscoding.newsapp.domain.setting

import com.carloscoding.newsapp.repository.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GetPreferencesUseCase @Inject constructor(val repository: SettingRepository) {
    suspend operator fun invoke(): Output {
        val result = repository.getPreferences()
        return withContext(Dispatchers.IO) {
            result.takeIfSuccess()?.let {
                Output.Success(it)
            } ?: Output.Error(result.takeError())
        }
    }

    sealed class Output {
        data class Success(val pref: List<String>) : Output()
        data class Error(val exception: Exception) : Output()
    }
}