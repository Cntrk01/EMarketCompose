package com.emarket.emarketcompose.components.bottom_navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
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
import com.emarket.emarketcompose.utils.customClickable
import com.emarket.emarketcompose.utils.dimensionResourceSp

@Composable
fun EMarketBottomNavigation(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    showIconText: String = "",
    badgeStatus: Boolean = false,
    badgeCount: Int = 0,
    selectedItemPosition: Int,
    isSelected: Boolean,
    clickItem: (Int) -> Unit
) {

    Column(
        modifier = modifier
            .customClickable {
                clickItem(selectedItemPosition)
            }
            .padding(dimensionResource(id = R.dimen._5dp))
            .fillMaxWidth()
            .background(
                color = if (isSelected) colorResource(id = R.color.white)
                else Color.Transparent,
                CircleShape
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        IconWithBadge(
            icon = icon,
            badgeStatus = badgeStatus,
            badgeCount = badgeCount,
            iconTint = colorResource(id = R.color.black)
        )

        if (showIconText.isNotEmpty()) {
            EMarketText(
                modifier = Modifier.wrapContentWidth(),
                text = showIconText,
                textAlign = TextAlign.Center,
                fontSize = dimensionResourceSp(id = R.dimen._14sp),
                textColor = colorResource(id = R.color.black)
            )
        }
    }
}

@Composable
fun IconWithBadge(
    @DrawableRes icon: Int,
    badgeStatus: Boolean,
    badgeCount: Int,
    iconTint : Color
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Icon",
            tint = iconTint
        )
        if (badgeStatus) {
            Badge(
                count = badgeCount,
                modifier = Modifier
                    .offset(
                        x = dimensionResource(id = R.dimen._13dp),
                        y = (-dimensionResource(id = R.dimen._10dp))
                    )
            )
        }
    }
}

@Composable
fun Badge(
    count: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(dimensionResource(id = R.dimen._24dp))
            .background(Color.Red, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        EMarketText(
            text = count.toString(),
            textColor = Color.White,
            fontSize = dimensionResourceSp(id = R.dimen._14sp)
        )
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
        selectedItemPosition = 5,
        isSelected = true,
        clickItem = {}
    )
}