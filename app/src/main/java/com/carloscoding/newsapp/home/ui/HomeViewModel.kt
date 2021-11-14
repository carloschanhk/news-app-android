package com.carloscoding.newsapp.home.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carloscoding.newsapp.home.usecase.ArticleOutput
import com.carloscoding.newsapp.home.usecase.GetHeadlinesByCategoryUseCase
import com.carloscoding.newsapp.home.usecase.GetTopHeadlinesUseCase
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getHeadlinesByCategoryUseCase: GetHeadlinesByCategoryUseCase,
    val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {

    //TODO: scroll physics, fucking blue
    //TODO: Refresh

    private val _homeState = MutableLiveData(HomeState())
    val homeState: LiveData<HomeState> = _homeState

    var getHeadlineJob: Job? = null

    // TODO: Hardcoded list, replace by shared preference later
    val categories: List<String> = listOf(
        "Today",
        "Business",
        "Entertainment",
        "Health",
        "Science",
        "Sports",
        "Technology",
    )

    init {
        onGettingArticles("Today", false)
    }


    fun onHomePageEvent(event: HomePageEvent) {
        when (event) {
            is HomePageEvent.OnArticleClicked -> {

            }
            is HomePageEvent.OnToggleCategory -> {
                _homeState.value = HomeState(
                    articlesPresented = listOf(),
                    selectedCategory = event.category,
                    errorMessage = null,
                    isLoading = true
                )
                onGettingArticles(event.category, false)
            }
            is HomePageEvent.OnRefreshCategory -> {
                _homeState.value = HomeState(
                    articlesPresented = listOf(),
                    selectedCategory = event.category,
                    errorMessage = null,
                    isLoading = true
                )
                onGettingArticles(event.category, true)
            }
        }
    }

    private fun onGettingArticles(category: String, isRefresh: Boolean) {
        getHeadlineJob?.cancel()
        getHeadlineJob = viewModelScope.launch {
            val result: ArticleOutput = if (category == "Today") {
                val param = categories.toMutableList().apply {
                    removeFirst()
                }
                getTopHeadlinesUseCase(param, isRefresh)
            } else {
                getHeadlinesByCategoryUseCase(category, isRefresh)
            }

            when (result) {
                is ArticleOutput.Error -> {
                    val errorMessage: String = when (result.exception) {
                        is IOException -> {
                            result.exception.message ?: "Network Error, please try again"
                        }
                        is HttpException -> {
                            result.exception.message ?: "Something wrong on the server, please try again later."
                        }
                        else -> {
                            result.exception.message ?: "Something unexpected happens"
                        }
                    }
                    _homeState.value =
                        homeState.value?.copy(errorMessage = errorMessage, isLoading = false)
                }
                is ArticleOutput.Success -> {
                    _homeState.value = homeState.value?.copy(
                        articlesPresented = result.articles,
                        errorMessage = null,
                        isLoading = false
                    )
                }
            }
        }
    }
}