package com.carloscoding.newsapp.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    // TODO: Hardcoded list, replace by shared preference later
    private val _categories = MutableLiveData(
        listOf(
            "Today",
            "Business",
            "Entertainment",
            "Health",
            "Science",
            "Sports",
            "Technology",
        )
    )
    val categories: LiveData<List<String>> = _categories

}