package com.emarket.emarketcompose.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme

@Composable
fun EMarketHeader(
    modifier: Modifier = Modifier,
    headerTitle: String = "E-Market",
    headerPadding: Dp = 5.dp,
    headerType: HeaderType = HeaderType.SIMPLE,
    backClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.primaryColor))
            .padding(headerPadding),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (headerType == HeaderType.MULTI) {
            Icon(
                modifier = Modifier
                    .clickable {
                        backClick()
                    }
                    .padding(start = 5.dp),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = colorResource(id = R.color.headerTitleIconColor)
            )
        }

        EMarketText(
            modifier = Modifier.padding(start = 15.dp),
            text = headerTitle,
            fontSize = 20.sp,
            textColor = colorResource(id = R.color.headerTitleIconColor),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview
fun PreviewHeader() {
    EMarketComposeTheme {
        EMarketHeader(headerTitle = "Hello World", headerType = HeaderType.MULTI)
    }
}





