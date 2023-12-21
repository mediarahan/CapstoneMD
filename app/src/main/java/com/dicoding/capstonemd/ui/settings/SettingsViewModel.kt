package com.dicoding.capstonemd.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.capstonemd.pref.UserPreference
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: UserPreference) : ViewModel() {
    fun getHiddenGemSetting(): LiveData<Boolean> {
        return pref.isHiddenGem().asLiveData()
    }

    fun saveHiddenGemSetting(isHiddenGem: Boolean) {
        viewModelScope.launch {
            pref.saveHiddenGemSetting(isHiddenGem)
        }
    }
}
