package com.emarket.emarketcompose.components.button

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme

@Composable
fun EMarketButton(
    modifier: Modifier = Modifier,
    text:String,
    textColor : Color = colorResource(id = R.color.white),
    color : Color = colorResource(id = R.color.primaryColor),
    fontWeight: FontWeight = FontWeight.W300,
    padding: Dp = dimensionResource(id = R.dimen._0dp),
    clickButton: () -> Unit,
    shape : Dp = dimensionResource(id = R.dimen._5dp)
) {
    Button(
        modifier = modifier
            .padding(padding),
        colors = ButtonDefaults.buttonColors(color),
        onClick = { clickButton() },
        shape = RoundedCornerShape(shape)
    )
    {
        EMarketText(
            text = text,
            fontWeight= fontWeight,
            textAlign = TextAlign.Center,
            textColor = textColor
        )
    }
}

@Composable
@Preview
fun PreviewEMarketButton() {
    EMarketComposeTheme {
        EMarketButton(text = "Add to Cart",clickButton = { /*TODO*/ }, textColor = colorResource(id = R.color.cardTitleColor))
    }
}