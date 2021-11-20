package com.carloscoding.newsapp.common_ui.article

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.domain.favourite.CheckIfArticleBookmarkedUseCase
import com.carloscoding.newsapp.domain.favourite.DeleteArticleUseCase
import com.carloscoding.newsapp.domain.favourite.InsertArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val checkIfArticleBookmarkedUseCase: CheckIfArticleBookmarkedUseCase,
    val insertArticleUseCase: InsertArticleUseCase,
    val deleteArticleUseCase: DeleteArticleUseCase
) : ViewModel() {
    private val _isBookmarked = MutableLiveData(false)
    val isBookmarked: LiveData<Boolean> = _isBookmarked
    lateinit var article: Article

    fun initViewModel(article:Article){
        this.article = article
        viewModelScope.launch {
            when(val result = checkIfArticleBookmarkedUseCase.invoke(article)){
                is CheckIfArticleBookmarkedUseCase.Output.Error -> {
                    Log.d("Testing", "initViewModel: ${result.exception.message}")
                }
                is CheckIfArticleBookmarkedUseCase.Output.Success -> {
                    _isBookmarked.value = result.isBookmarked
                }
            }
        }
    }

    fun onBookmarkIconClicked(){
        viewModelScope.launch{
            isBookmarked.value?.let {
                if (it) {
                    deleteArticleUseCase.invoke(article)
                    _isBookmarked.value = false
                } else {
                    insertArticleUseCase.invoke(article)
                    _isBookmarked.value = true
                }
            }
        }
    }
}