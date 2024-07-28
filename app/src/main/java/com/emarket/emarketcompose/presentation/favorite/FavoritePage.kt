package com.emarket.emarketcompose.presentation.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.text.EMarketText

@Composable
fun FavoritePage(
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    favoriteViewModel.refreshFavorites()

    val favoriteItems by favoriteViewModel.favoriteList.collectAsState()

    LazyColumn(
        modifier = Modifier
    ) {
        items(favoriteItems.size) { favorite ->
            Card(
                modifier = Modifier.padding(dimensionResource(id = R.dimen._15dp)),
                elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen._10dp)),
                colors = CardDefaults.cardColors(colorResource(id = R.color.cardColor)),
            ) {
                Box {
                    AsyncImage(
                        modifier = Modifier.height(dimensionResource(id = R.dimen._150dp)),
                        model = favoriteItems[favorite].image,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop
                    )

                    Image(
                        modifier = Modifier
                            .fillMaxSize(fraction = 0.15f)
                            .padding(dimensionResource(id = R.dimen._10dp))
                            .align(alignment = Alignment.TopEnd)
                            .clickable {
                                favoriteViewModel.removeFromFavorite(eMarketItem = favoriteItems[favorite])
                            },
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "Favorite"
                    )
                }

                Column(
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen._5dp),
                        end = dimensionResource(id = R.dimen._5dp),
                        bottom = dimensionResource(id = R.dimen._5dp),
                    )

                ) {
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._5dp)))

                    EMarketText(
                        text = favoriteItems[favorite].name,
                        minLines = 1,
                        maxLines = 1,
                        textColor = colorResource(id = R.color.cardTitleColor)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._5dp)))

                    EMarketText(
                        text = favoriteItems[favorite].description,
                        maxLines = 2,
                        minLines = 1,
                        textColor = colorResource(id = R.color.cardTitleColor)
                    )
                }
            }
        }
    }
}