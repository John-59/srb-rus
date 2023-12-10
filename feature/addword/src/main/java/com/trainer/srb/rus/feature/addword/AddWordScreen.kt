package com.trainer.srb.rus.feature.addword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddWordScreen(
    viewModel: AddWordViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = "",
            placeholder = {
                Text(text = "Сербский (латиница)")
            },
            onValueChange = viewModel::srbLatinChange
        )
        TextField(
            value = "",
            placeholder = {
                Text(text = "Сербский (кириллица)")
            },
            onValueChange = viewModel::srbCyrillicChange
        )
        TextField(
            value = "",
            placeholder = {
                Text(text = "Русский")
            },
            onValueChange = viewModel::rusChange
        )
        Button(
            onClick = viewModel::add
        ) {
            Text(text = "Добавить в словарь")
        }
    }
}

@Composable
@Preview(apiLevel = 33)
private fun AddWordScreenPreview() {
    AddWordScreen()
}