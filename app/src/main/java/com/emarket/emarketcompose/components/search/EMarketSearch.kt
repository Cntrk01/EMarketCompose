package com.emarket.emarketcompose.components.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ElevatedTextField(
    modifier: Modifier = Modifier,
    searchPadding: Dp = 0.dp,
    roundedShape: Dp = 10.dp,
    hint: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier.fillMaxWidth().padding(searchPadding),
        value = hint,
        onValueChange = {
            onValueChange(it)
        },
        minLines = 1,
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        shape = RoundedCornerShape(roundedShape)
    )
}

@Preview
@Composable
fun PreviewElevatedTextField() {
    ElevatedTextField(
        hint = "Search",
        onValueChange = {}
    )
}