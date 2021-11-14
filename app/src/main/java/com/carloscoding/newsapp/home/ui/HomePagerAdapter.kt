package com.carloscoding.newsapp.home.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.carloscoding.newsapp.common_ui.news.NewsFragment

class HomePagerAdapter(parentFragment: Fragment): FragmentStateAdapter(parentFragment) {
    var dataset = listOf<String>()

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun createFragment(position: Int): Fragment {
        return NewsFragment.newInstance(dataset[position])
    }
}