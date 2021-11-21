package com.carloscoding.newsapp.favourite.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.domain.favourite.DeleteArticleUseCase
import com.carloscoding.newsapp.domain.favourite.GetFavouriteArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getFavouriteArticlesUseCase: GetFavouriteArticlesUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
) : ViewModel() {

    private val _favouriteArticles = MutableLiveData<List<Article>>(listOf())
    val favouriteArticles : LiveData<List<Article>> = _favouriteArticles

    var job : Job? = null

    init {
        viewModelScope.launch {
            when (val result = getFavouriteArticlesUseCase.invoke()) {
                is GetFavouriteArticlesUseCase.Output.Error -> {
                    Log.d("Testing", "Error in favourite fragment: ${result.exception.message}")
                }
                is GetFavouriteArticlesUseCase.Output.Success -> {
                    job = result.articlesFlow.onEach {
                        _favouriteArticles.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun deleteArticleFromLocal(article: Article){
        viewModelScope.launch {
            deleteArticleUseCase.invoke(article)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}