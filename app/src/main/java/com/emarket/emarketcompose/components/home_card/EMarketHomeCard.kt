package com.emarket.emarketcompose.components.home_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.button.EMarketButton
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme

@Composable
fun EMarketHomeCard(
    modifier: Modifier = Modifier,
    shape: Dp = dimensionResource(id = R.dimen._5dp),
    elevation: Dp = dimensionResource(id = R.dimen._2dp),
    image: String,
    price: String,
    description: String,
    colors: Int = R.color.cardColor,
    clickAddToCardButton: () -> Unit,
    clickFavorite: () -> Unit,
    clickDetail : () -> Unit = {},
    isShowStar : Boolean = false
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = dimensionResource(id = R.dimen._10dp))
            .clickable {
                clickDetail()
            },
        elevation = CardDefaults.elevatedCardElevation(elevation),
        shape = RoundedCornerShape(shape),
        colors = CardDefaults.cardColors(colorResource(id = colors))
    ) {
        Column(
            modifier = modifier
                .padding(dimensionResource(id = R.dimen._5dp))
                .fillMaxWidth()
        ) {
            Box {
                AsyncImage(
                    modifier = Modifier.height(250.dp),
                    model = image,
                    contentDescription = "image",
                    contentScale = ContentScale.Crop
                )

                Image(
                    modifier = Modifier
                        .fillMaxSize(fraction = 0.30f)
                        .padding(dimensionResource(id = R.dimen._10dp))
                        .align(alignment = Alignment.TopEnd)
                        .clickable {
                            clickFavorite()
                        },
                    painter = if (isShowStar) painterResource(id = R.drawable.star)
                              else painterResource(id = R.drawable.un_star),
                    contentDescription = "Favorite"
                )
            }

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._5dp),))

            EMarketText(text = price, textColor = colorResource(id = R.color.primaryColor))

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._5dp),))

            EMarketText(text = description, textColor = colorResource(id = R.color.cardTitleColor))

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._5dp),))

            EMarketButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.add_to_card),
                clickButton = { clickAddToCardButton() }
            )
        }
    }
}

@Preview
@Composable
fun PreviewEMarketHomeCard() {
    EMarketComposeTheme {
        EMarketHomeCard(
            image =  R.drawable.star.toString(),
            description = "Description",
            price = "Price",
            clickAddToCardButton = {

            },
            clickFavorite = {
                true
            },
            isShowStar = true
        )
    }
}

