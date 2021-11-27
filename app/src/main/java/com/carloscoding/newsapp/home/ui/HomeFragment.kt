package com.carloscoding.newsapp.home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.carloscoding.newsapp.common_ui.news.ArticleAdapter
import com.carloscoding.newsapp.databinding.FragmentHomeBinding
import com.carloscoding.newsapp.setting.SettingDialogFragment
import com.carloscoding.newsapp.setting.SettingDialogFragment.Companion.SETTING_DIALOG
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = homeViewModel
            lifecycleOwner = viewLifecycleOwner
            fragment = this@HomeFragment
            homeViewPager.adapter = HomePagerAdapter(this@HomeFragment)
            TabLayoutMediator(homeTabLayout, homeViewPager){ tab, position ->
                tab.text = homeViewModel.categories.value?.get(position)
            }.attach()
        }

        setFragmentResultListener(SETTING_DIALOG){ _, _ ->
            homeViewModel.retrievePreference()
        }
    }

    fun onSettingButtonClicked(){
        SettingDialogFragment.showSettingDialog(parentFragmentManager)
    }

}