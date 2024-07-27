package com.emarket.emarketcompose.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.usecase.db.FavoriteUseCase
import com.emarket.emarketcompose.presentation.base_viewmodel.FavoriteBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase,
) : FavoriteBaseViewModel(favoriteUseCase) {

    var isFavorite = mutableStateOf(false)

    fun checkFavorite(eMarketItem: String) = viewModelScope.launch(Dispatchers.IO) {
        val status = favoriteUseCase.checkExistProduct(eMarketItem)
        //IO içerisinde işlem yaptığımız için ve isFavorite değerine Main threadden erişmeye çalıştığımız için context değiştirmeliyiz.
        withContext(Dispatchers.Main){
            isFavorite.value = status
        }
    }

    fun favoriteButtonAction(
        eMarketItem: EMarketItem
    ){
        if (isFavorite.value) {
            removeFromFavorite(eMarketItem = eMarketItem)
            isFavorite.value = false
        }else{
            addToFavorite(eMarketItem = eMarketItem)
            isFavorite.value = true
        }
    }
}