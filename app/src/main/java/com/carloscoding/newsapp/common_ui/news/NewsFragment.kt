package com.carloscoding.newsapp.common_ui.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.carloscoding.newsapp.common_ui.article.ArticleFragmentDirections
import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    companion object {
        const val NEWS_CATEGORY = "news_category"

        fun newInstance(category: String) = NewsFragment().apply {
            arguments = Bundle().apply {
                putString(NEWS_CATEGORY, category)
            }
        }
    }

    private val newsViewModel : NewsViewModel by viewModels()
    lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = arguments?.getString(NEWS_CATEGORY)
        category?.let{
            newsViewModel.initViewModel(it)
        }

        binding.apply {
            viewModel = newsViewModel
            lifecycleOwner = viewLifecycleOwner
            rvArticles.adapter = ArticleAdapter().apply {
                onNewsItemClickListener = ::onNewsItemClicked
            }
        }
    }

    private fun onNewsItemClicked(article: Article){
        val action = ArticleFragmentDirections.actionGlobalArticleFragment(article)
        findNavController().navigate(action)
    }

}