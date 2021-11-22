package com.carloscoding.newsapp.search.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.carloscoding.newsapp.common_ui.article.ArticleFragmentDirections
import com.carloscoding.newsapp.common_ui.news.ArticleAdapter
import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = searchViewModel
            rvSearchArticles.adapter = ArticleAdapter().apply {
                onNewsItemClickListener = ::onNewsItemClicked
            }
            etSearchBox.apply {
                setOnKeyListener { _, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                        hideKeyboard()
                        clearFocus()
                        searchViewModel.onSearch(text.toString())
                        text.clear()
                        return@setOnKeyListener true
                    }
                    false
                }
            }

        }
    }

    private fun onNewsItemClicked(article: Article) {
        Log.d("Testing", "onNewsItemClicked: ")
        val action = ArticleFragmentDirections.actionGlobalArticleFragment(article)
        findNavController().navigate(action)
    }

    private fun hideKeyboard(){
        val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        manager?.hideSoftInputFromWindow(binding.etSearchBox.windowToken,0)
    }
}