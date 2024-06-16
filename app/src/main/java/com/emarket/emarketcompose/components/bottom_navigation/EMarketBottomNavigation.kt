package com.emarket.emarketcompose.components.bottom_navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.utils.dimensionResourceSp

@Composable
fun EMarketBottomNavigation(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    showIconText: String = "",
    badgeStatus: Boolean = false,
    badgeCount: Int = 0,
    clickItem : () -> Unit
) {
    //TODO : Row çağırıldığı yerde verilmeli backgroundda ordan verilmeli
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen._5dp))
            .clickable {
                clickItem()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Icon"
            )
            if (badgeStatus) {
                Box(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen._15dp),
                            end = dimensionResource(id = R.dimen._15dp)
                        )
                        .size(dimensionResource(id = R.dimen._35dp))
                        .background(Color.Red, CircleShape)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    EMarketText(
                        text = badgeCount.toString(),
                        textColor = Color.White,
                        fontSize = dimensionResourceSp(id = R.dimen._18sp),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10dp)))

        if (showIconText.isNotEmpty()) {
            EMarketText(
                modifier = Modifier.wrapContentWidth(),
                text = showIconText,
                textAlign = TextAlign.Center,
                fontSize = dimensionResourceSp(id = R.dimen._22sp),
                textColor = colorResource(id = R.color.bottomNavTextColor)
            )
        }
    }
}

@Preview
@Composable
fun EMarketBottomNavigationPreview() {
    EMarketBottomNavigation(
        icon = R.drawable.un_star,
        showIconText = "Canturk",
        badgeStatus = true,
        badgeCount = 15,
        clickItem={

        }
    )
}