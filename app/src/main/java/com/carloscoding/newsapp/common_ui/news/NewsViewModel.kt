package com.carloscoding.newsapp.common_ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carloscoding.newsapp.home.usecase.ArticleOutput
import com.carloscoding.newsapp.home.usecase.GetHeadlinesByCategoryUseCase
import com.carloscoding.newsapp.home.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val getHeadlinesByCategoryUseCase: GetHeadlinesByCategoryUseCase,
    val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {

    //TODO: Refresh
    //TODO: Article clicked

    private val _newsState = MutableLiveData(NewsState())
    val newsState: LiveData<NewsState> = _newsState

    var getHeadlineJob : Job? = null

    lateinit var category: String

    // TODO: Hardcoded list, replace by shared preference later
    private val categories: List<String> = listOf(
        "Today",
        "Business",
        "Entertainment",
        "Health",
        "Science",
        "Sports",
        "Technology",
    )

    fun initViewModel(category: String){
        this.category = category
        onGettingArticles(category, isRefresh = false)
    }

    fun onNewsFragmentEvent(newsFragEvent: NewsFragEvent){
        when (newsFragEvent){
            is NewsFragEvent.OnArticleClicked -> {

            }
            is NewsFragEvent.OnRefresh -> {
                // TODO: Change State
                onGettingArticles(category, isRefresh = true)
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
                    _newsState.value =
                        newsState.value?.copy(errorMessage = errorMessage, isLoading = false)
                }
                is ArticleOutput.Success -> {
                    _newsState.value = newsState.value?.copy(
                        articlesPresented = result.articles,
                        errorMessage = null,
                        isLoading = false
                    )
                }
            }
        }
    }
}