package com.architecture.feature_marketplace.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.architecture.feature_marketplace.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    var textValue by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier,
        textStyle = TextStyle(fontSize = 14.sp),

        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = stringResource(id = R.string.search_box_content_description)
            )
        },
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_box_placeholder),
                style = TextStyle(fontSize = 14.sp),
            )
        },
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChange(it)
        },
    )
}

@Preview
@Composable
fun PreviewSearchBox() {
    SearchBox(onValueChange = { })
}
