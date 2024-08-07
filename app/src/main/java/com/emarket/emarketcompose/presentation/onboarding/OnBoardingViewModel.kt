package com.emarket.emarketcompose.presentation.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.usecase.splash_screen.ManageAppEntryUseCase
import com.emarket.emarketcompose.navigations.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val manageAppEntryUseCase: ManageAppEntryUseCase) : ViewModel() {

    var splashCondition by mutableStateOf(true)
    var startDestination by mutableStateOf("")
        private set

    init {
       readDataStore()
    }

    private fun readDataStore() = viewModelScope.launch (Dispatchers.IO){
        manageAppEntryUseCase.readAppEntry.readData().collectLatest {
            startDestination = if (it){
                NavigationState.AppRoute.route
            }else{
                NavigationState.OnBoardingRoute.route
            }
        }
    }

    fun updateDataStore() = viewModelScope.launch(Dispatchers.IO) {
        manageAppEntryUseCase.saveAppEntry.saveData()
    }
}