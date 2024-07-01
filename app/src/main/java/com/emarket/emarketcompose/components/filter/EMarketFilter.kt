package com.emarket.emarketcompose.components.filter

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.emarket.emarketcompose.R
import com.emarket.emarketcompose.components.text.EMarketText
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme

@SuppressLint("MutableCollectionMutableState")
@Composable
fun EMarketFilter(
    filterList: List<String>,
    modifier: Modifier = Modifier,
    checkBoxList: ((ArrayList<String>) -> Unit)? = null,
    radioItem: ((String) -> Unit)? = null,
    filterType: FilterType = FilterType.CHECKBOX
) {
    //setOf ile istediğim gibi çalışırken HashSet ile ekleme çıkarma yapamadım
    var checkItemPosition by remember { mutableStateOf(setOf<Int>()) }

    val mutableCheckBoxList by remember { mutableStateOf(arrayListOf<String>()) }

    var selectedRadioOption by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.background))
    ) {
        if (filterType == FilterType.CHECKBOX) {
            filterList.forEachIndexed { index, text ->
                Row(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen._10dp),
                            bottom = dimensionResource(id = R.dimen._10dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkItemPosition.contains(index),
                        onCheckedChange = {
                            if (it) {
                                checkItemPosition = checkItemPosition + index
                                mutableCheckBoxList.add(text)
                            } else {
                                checkItemPosition = checkItemPosition - index
                                mutableCheckBoxList.remove(text)
                            }
                            checkBoxList?.invoke(mutableCheckBoxList)
                        },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = colorResource(id = R.color.headerTitleIconColor),
                            checkedColor = colorResource(id = R.color.primaryColor),
                            uncheckedColor = colorResource(id = R.color.primaryColor),
                            disabledCheckedColor = colorResource(id = R.color.primaryColor)
                        )
                    )

                    Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen._10dp)))

                    EMarketText(
                        text = text,
                        textColor = colorResource(id = R.color.bottomNavTextColor)
                    )
                }
            }
        }

        if (filterType == FilterType.RADIO) {
            filterList.forEach { text ->
                Row(
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen._10dp),
                        bottom = dimensionResource(id = R.dimen._10dp)
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedRadioOption == text,
                        onClick = {
                            selectedRadioOption = text
                            radioItem?.invoke(selectedRadioOption)
                        },
                        colors = RadioButtonDefaults.colors(
                            disabledSelectedColor = colorResource(id = R.color.primaryColor),
                            selectedColor = colorResource(id = R.color.primaryColor),
                            disabledUnselectedColor = colorResource(id = R.color.primaryColor),
                            unselectedColor = colorResource(id = R.color.primaryColor)
                        )
                    )

                    Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen._10dp)))

                    EMarketText(
                        text = text,
                        textColor = colorResource(id = R.color.bottomNavTextColor)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun EMarketFilterPreview() {
    EMarketComposeTheme {
        EMarketFilter(
            filterList = listOf("Hi", "123", "Hello"),
            checkBoxList = {}
        )
    }
}