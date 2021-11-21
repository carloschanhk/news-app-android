package com.carloscoding.newsapp.favourite.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.carloscoding.newsapp.R
import com.carloscoding.newsapp.common_ui.article.ArticleFragmentDirections
import com.carloscoding.newsapp.common_ui.news.ArticleAdapter
import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.databinding.FragmentFavouriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = favouriteViewModel
            rvFavouriteArticles.adapter = ArticleAdapter().apply {
                onNewsItemClickListener = ::onNewsItemClicked
                onNewsItemLongClickListener = ::onNewsItemLongClicked
            }
        }
    }

    private fun onNewsItemClicked(article: Article) {
        val action = ArticleFragmentDirections.actionGlobalArticleFragment(article)
        findNavController().navigate(action)
    }

    private fun onNewsItemLongClicked(article: Article) {
        AlertDialog.Builder(context)
            .setTitle(R.string.delete_dialog_title)
            .setMessage(R.string.delete_dialog_message)
            .setPositiveButton("OK") { dialog, _ ->
                favouriteViewModel.deleteArticleFromLocal(article)
                dialog.dismiss()
            }
            .setNegativeButton("No"){
                dialog, _ -> dialog.dismiss()
            }.create().show()
    }

}