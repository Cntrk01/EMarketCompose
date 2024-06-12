package com.emarket.emarketcompose.components.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme

@Composable
fun EMarketText(
    modifier: Modifier = Modifier,
    text: String,
    fontStyle: FontStyle = FontStyle.Normal,
    textColor : Color = Color.White,
    fontSize : TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines : Int = 1,
    minLines : Int = 1,
    textAlign : TextAlign ?= null
) {

    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        fontStyle = fontStyle,
        color = textColor,
        fontSize = fontSize,
        maxLines = maxLines,
        minLines = minLines,
        fontWeight = fontWeight,
        textAlign = textAlign ?: TextAlign.Start
    )
}

@Preview
@Composable
fun PreviewEMarketText(){
    EMarketComposeTheme {
        EMarketText(text = "Hello My World")
    }
}