package com.emarket.emarketcompose.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.repository.model.BasketItem
import com.emarket.emarketcompose.domain.usecase.local.BasketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketUseCase: BasketUseCase
) : ViewModel() {

    private var _basket : MutableLiveData<List<BasketItem>> = MutableLiveData()
    val basket : LiveData<List<BasketItem>> get() = _basket

    fun getBasket() = viewModelScope.launch (Dispatchers.IO){
        withContext(Dispatchers.Main) {
            _basket.value = basketUseCase.getAllBasket()
        }
    }

    fun deleteBasketItem(productId : String) = viewModelScope.launch (Dispatchers.IO){
        basketUseCase.deleteProductFromBasket(productId = productId)
    }

    fun addBasketItem(basketItem: BasketItem) = viewModelScope.launch (Dispatchers.IO){
        basketUseCase.addProductInBasket(product = basketItem)
    }

    fun completeBasket() = viewModelScope.launch (Dispatchers.IO){
        basketUseCase.completeBasket()
    }
}