package com.emarket.emarketcompose.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.usecase.app_entry.ManageAppEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val manageAppEntryUseCase: ManageAppEntryUseCase) : ViewModel() {

    private val _readStatus : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val readStatus : StateFlow<Boolean> get() = _readStatus

    init {
       readDataStore()
    }

    private fun readDataStore() = viewModelScope.launch(Dispatchers.IO) {
        manageAppEntryUseCase.readAppEntry.readData().collectLatest {
            _readStatus.value=it
        }
    }

    fun updateDataStore() = viewModelScope.launch(Dispatchers.IO) {
        manageAppEntryUseCase.saveAppEntry.saveData()
    }
}