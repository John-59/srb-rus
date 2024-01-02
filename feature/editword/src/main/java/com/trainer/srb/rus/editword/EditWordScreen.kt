package com.trainer.srb.rus.editword

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EditWordScreen(
    viewModel: EditWordViewModel = hiltViewModel(),
    onBack: () -> Unit
) {

}

@Preview(apiLevel = 33)
@Composable
fun EditWordScreenPreview() {
    EditWordScreen(
        onBack = {}
    )
}