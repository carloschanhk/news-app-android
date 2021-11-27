package com.carloscoding.newsapp.home.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carloscoding.newsapp.domain.setting.GetPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPreferencesUseCase: GetPreferencesUseCase
) : ViewModel() {
    private val _categories = MutableLiveData<List<String>>(listOf())
    val categories: LiveData<List<String>> = _categories

    init {
        retrievePreference()
    }

    fun retrievePreference(){
        viewModelScope.launch {
            when (val result = getPreferencesUseCase.invoke()) {
                is GetPreferencesUseCase.Output.Error -> {
                    Log.d("Testing", "Error in Home Fragment: ${result.exception.message}")
                }
                is GetPreferencesUseCase.Output.Success -> {
                    _categories.value = result.pref.toMutableList().apply {
                        add(0,"Today")
                    }
                }
            }
        }
    }
}