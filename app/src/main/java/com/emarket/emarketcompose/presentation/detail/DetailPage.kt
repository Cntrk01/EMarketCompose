package com.emarket.emarketcompose.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.button.EMarketButton
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme
import com.emarket.emarketcompose.utils.customClickable
import com.emarket.emarketcompose.utils.dimensionResourceSp

@Composable
fun DetailPage(
    modifier: Modifier = Modifier,
    eMarketItem: EMarketItem,
    clickAddToCardButton: (EMarketItem) -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    detailViewModel.checkFavorite(eMarketItem.itemId)

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen._15dp))
            .fillMaxSize()
    ) {
        Box {
            AsyncImage(
                modifier = Modifier.height(250.dp),
                model = eMarketItem.image,
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )

            Image(
                modifier = Modifier
                    .align(alignment = Alignment.TopEnd)
                    .padding(dimensionResource(id = R.dimen._10dp))
                    .fillMaxHeight(fraction = 0.05f)
                    .customClickable {
                        detailViewModel.favoriteButtonAction(eMarketItem = eMarketItem)
                    },
                painter = if (detailViewModel.isFavorite.value) painterResource(id = R.drawable.star)
                else painterResource(id = R.drawable.un_star),
                contentDescription = "Favorite"
            )
        }

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._5dp)))

        EMarketText(
            text = eMarketItem.name,
            textColor = colorResource(id = R.color.cardTitleColor),
            fontSize = dimensionResourceSp(id = R.dimen._20sp),
            minLines = 1,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._5dp)))

        EMarketText(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            text = eMarketItem.description,
            textColor = colorResource(id = R.color.cardTitleColor),
            fontSize = dimensionResourceSp(id = R.dimen._16sp),
            fontWeight = FontWeight.Normal,
            minLines = 1,
            maxLines = 50
        )

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._2dp)))
        //Bottomnav animasyonlu invisible olduğu için bu sayfa tam açılmıyor sonra tam boyuta geçiyor bundan dolayı da bu row aşağı kayıyor gibi gözüküyor
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            EMarketText(
                text = "Price : $${eMarketItem.price}",
                textColor = colorResource(id = R.color.primaryColor),
                maxLines = 1,
                minLines = 1,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.weight(0.35f))

            EMarketButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                text = stringResource(R.string.add_to_card),
                clickButton = {
                    clickAddToCardButton(eMarketItem)
                }
            )
        }
    }
}

@Preview
@Composable
fun DetailPagePreview() {
    EMarketComposeTheme {
        DetailPage(
            eMarketItem = EMarketItem(
                itemId = "1",
                image = R.drawable.empty_image.toString(),
                price = "12",
                name = "HELLOW",
                description = "ASDSADASDD",
                filterItem = FilterItem(model = "Model", brand = "Brand")
            ), clickAddToCardButton ={

            }
        )
    }
}