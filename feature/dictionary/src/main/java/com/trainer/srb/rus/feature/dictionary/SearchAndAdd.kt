package com.trainer.srb.rus.feature.dictionary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons
import com.trainer.srb.rus.core.translation.Word

@Composable
fun SearchAndAdd(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onAddClicked: (Word) -> Unit,
    onResetSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.displayMedium,
        placeholder = {
            Text(
                text = "Поиск слов",
                style = MaterialTheme.typography.displayMedium,
            )
        },
        leadingIcon = {
            if (value.text.isBlank()) {
                Icon(
                    imageVector = SrIcons.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            } else {
                Icon(
                    imageVector = SrIcons.AddWord,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        if (value.text.isNotBlank()) {
                            onAddClicked(Word.Serbian(latinValue = value.text, cyrillicValue = ""))
                        }
                    }
                )
            }
        },
        trailingIcon = {
            Icon(
                imageVector = SrIcons.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable {
                    onResetSearch()
                }
            )
        }
    )
}

@PreviewLightDark
@Composable
fun SearchAndAddEmptyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        SearchAndAdd(
            value = TextFieldValue(""),
            onValueChange = {},
            onAddClicked = {},
            onResetSearch = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@PreviewLightDark
@Composable
fun SearchAndAddPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        SearchAndAdd(
            value = TextFieldValue("lubenica"),
            onValueChange = {},
            onAddClicked = {},
            onResetSearch = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}