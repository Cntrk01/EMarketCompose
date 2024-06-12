package com.emarket.emarketcompose.components.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme

@Composable
fun EMarketButton(
    modifier: Modifier = Modifier,
    text:String,
    color : Int = R.color.primaryColor,
    fontWeight: FontWeight = FontWeight.W300,
    padding: Dp = 0.dp,
    clickButton: () -> Unit,
    shape : Dp = 5.dp
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding),
        colors = ButtonDefaults.buttonColors(colorResource(id = color)),
        onClick = { clickButton() },
        shape = RoundedCornerShape(shape)
    )
    {
        EMarketText(text = text,fontWeight= fontWeight)
    }
}

@Composable
@Preview
fun PreviewEMarketButton() {
    EMarketComposeTheme {
        EMarketButton(text = "Add to Cart",clickButton = { /*TODO*/ })
    }
}