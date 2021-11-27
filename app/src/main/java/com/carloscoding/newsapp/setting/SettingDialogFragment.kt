package com.carloscoding.newsapp.setting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.carloscoding.newsapp.databinding.SettingDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingDialogFragment : BottomSheetDialogFragment() {

    private val settingViewModel: SettingViewModel by viewModels()
    lateinit var binding: SettingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = settingViewModel
            rvCategoryCheckbox.adapter = CheckboxAdapter()
        }
        settingViewModel.onApplyButtonClickerAction.observe(viewLifecycleOwner) {
            setFragmentResult(SETTING_DIALOG, bundleOf())
            dialog?.dismiss()
        }
    }

    companion object {
        const val SETTING_DIALOG = "setting_dialog"
        fun showSettingDialog(fm: FragmentManager) {
            newInstance().show(fm, SETTING_DIALOG)
        }

        fun newInstance(): SettingDialogFragment = SettingDialogFragment()
    }

}