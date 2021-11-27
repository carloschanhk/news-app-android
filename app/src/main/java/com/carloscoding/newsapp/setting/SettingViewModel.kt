package com.carloscoding.newsapp.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carloscoding.newsapp.domain.setting.GetPreferencesUseCase
import com.carloscoding.newsapp.domain.setting.SetPreferencesUseCase
import com.carloscoding.newsapp.utils.Constant.CONST_CATEGORIES
import com.carloscoding.newsapp.utils.OneOffEmptyEvent
import com.carloscoding.newsapp.utils.OneOffEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getPreferencesUseCase: GetPreferencesUseCase,
    val setPreferencesUseCase: SetPreferencesUseCase
) : ViewModel() {
    private val _checkboxCategories = MutableLiveData(listOf<CheckboxModel>())
    val checkboxCategories: LiveData<List<CheckboxModel>> = _checkboxCategories

    private val _onApplyButtonClickedAction = MutableLiveData<OneOffEmptyEvent>()
    val onApplyButtonClickerAction: LiveData<OneOffEmptyEvent> = _onApplyButtonClickedAction

    init {
        viewModelScope.launch {
            when (val result = getPreferencesUseCase.invoke()) {
                is GetPreferencesUseCase.Output.Error -> {
                    Log.d("Testing", "Error in setting fragment: ${result.exception.message}")
                }
                is GetPreferencesUseCase.Output.Success -> {
                    _checkboxCategories.value = CONST_CATEGORIES.map { category ->
                        CheckboxModel(
                            category = category,
                            isSelected = result.pref.any { it == category }
                        )
                    }
                }
            }
        }
    }

    fun onApplyButtonClicked(){
        viewModelScope.launch {
            val selectedCategories:List<String> = checkboxCategories.value?.filter { it.isSelected }?.map { it.category } ?: listOf()
            setPreferencesUseCase.invoke(selectedCategories)
            _onApplyButtonClickedAction.value = OneOffEmptyEvent()
        }
    }


}