package com.emarket.emarketcompose.presentation.favorite

import com.emarket.emarketcompose.domain.usecase.local.FavoriteUseCase
import com.emarket.emarketcompose.presentation.base_viewmodel.FavoriteBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase
) : FavoriteBaseViewModel(favoriteUseCase) {

}