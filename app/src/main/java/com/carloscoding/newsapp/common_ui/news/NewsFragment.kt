package com.carloscoding.newsapp.common_ui.news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.carloscoding.newsapp.R
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
            rvArticles.adapter = ArticleAdapter()
        }

    }


}