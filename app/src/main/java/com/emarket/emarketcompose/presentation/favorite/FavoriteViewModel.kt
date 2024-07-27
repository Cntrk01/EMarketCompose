package com.emarket.emarketcompose.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.usecase.db.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {

    private val _favoriteList = MutableStateFlow<List<EMarketItem>>(emptyList())
    val favoriteList: StateFlow<List<EMarketItem>> get() = _favoriteList

    fun refreshFavorites() = viewModelScope.launch {
        val list = withContext(Dispatchers.IO) {
            favoriteUseCase.getFavoriteList()
        }
        _favoriteList.value = list
    }

    fun removeFromFavorite(eMarketItem: EMarketItem) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            favoriteUseCase.deleteProducts(eMarketItem)
        }
        refreshFavorites()
    }

}