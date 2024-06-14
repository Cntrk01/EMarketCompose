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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
    shape: Dp = 5.dp,
    elevation: Dp = 0.dp,
    colors: Int = R.color.cardColor,
    image: String,
    price: String,
    description: String,
    clickButton : () -> Unit,
    clickFavorite : () -> Boolean
) {
    val checkImageStatus = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(elevation),
        shape = RoundedCornerShape(shape),
        colors = CardDefaults.cardColors(colorResource(id = colors))
    ) {
        Column(
            modifier = modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {

            Box {
                AsyncImage(
                    modifier = Modifier.height(200.dp),
                    model = image ?: R.drawable.empty_image,
                    contentDescription = "image"
                )

                Image(
                    modifier = Modifier
                        .fillMaxSize(fraction = 0.15f)
                        .padding(10.dp)
                        .align(alignment = Alignment.TopEnd)
                        .clickable {
                            checkImageStatus !=checkImageStatus
                            clickFavorite()
                        },
                    painter = if (checkImageStatus.value) painterResource(id = R.drawable.star) else painterResource(id = R.drawable.un_star),
                    contentDescription = "Favorite"
                )
            }

            Spacer(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))

            EMarketText(text = price, textColor = colorResource(id = R.color.primaryColor))

            Spacer(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))

            EMarketText(text = description, textColor = colorResource(id = R.color.cardTitleColor))

            Spacer(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))

            EMarketButton(
                text = stringResource(R.string.add_to_card),
                clickButton = { clickButton() }
            )
        }
    }
}

@Preview
@Composable
fun PreviewEMarketHomeCard() {
    EMarketComposeTheme {
        EMarketHomeCard(
            image = "R.drawable.ic_launcher_background",
            price = "15.000",
            description = "Iphone 13 Pro Max 256Gb",
            clickButton = {

            },
            clickFavorite = {
                true
            }
        )
    }
}