package com.emarket.emarketcompose.components.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.text.EMarketText

@Composable
fun EMarketSearch(
    modifier: Modifier = Modifier,
    searchPadding: Dp = dimensionResource(id = R.dimen._0dp),
    roundedShape: Dp = dimensionResource(id = R.dimen._10dp),
    hint: String = "Search",
    text : String = "",
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    var searchText by remember { mutableStateOf(text) }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(searchPadding),
        value = searchText,
        onValueChange = {
            searchText = it
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
        shape = RoundedCornerShape(roundedShape),
        placeholder = {
            EMarketText(
                text = hint,
                textColor = Icons.Default.Search.tintColor
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        ),

        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                keyboardController?.hide()
            }
        )
    )
}

@Preview
@Composable
fun PreviewElevatedTextField() {
    EMarketSearch(
        hint = "Search",
        onValueChange = {},
        onSearch = {}
    )
}