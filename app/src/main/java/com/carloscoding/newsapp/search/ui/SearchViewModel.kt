package com.carloscoding.newsapp.search.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carloscoding.newsapp.common_ui.news.NewsState
import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.domain.remote.ArticleOutput
import com.carloscoding.newsapp.domain.remote.SearchHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHeadlinesUseCase: SearchHeadlinesUseCase
) : ViewModel() {
    private val _newsState = MutableLiveData(NewsState(isLoading = false))
    val newsState: LiveData<NewsState> = _newsState

    fun onSearch(keyword: String) {
        viewModelScope.launch {
            _newsState.value = newsState.value?.copy(
                isLoading = true,
                errorMessage = null,
            )
            when (val result = searchHeadlinesUseCase.invoke(keyword)) {
                is ArticleOutput.Error -> {
                    _newsState.value = newsState.value?.copy(
                        articlesPresented = listOf(),
                        errorMessage = result.exception.message,
                        isLoading = false
                    )
                }
                is ArticleOutput.Success -> {
                    _newsState.value = newsState.value?.copy(
                        articlesPresented = result.articles,
                        errorMessage = if (result.articles.isEmpty()) "Cannot find any articles. Please try with another word." else null,
                        isLoading = false
                    )
                }
            }
        }

    }
}