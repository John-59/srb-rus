package com.trainer.srb.rus.feature.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AdminActionsScreen(
    viewModel: AdminActionsViewModel = hiltViewModel(),
    navigateToAddSrbRusTranslation: () -> Unit,
    navigateToAddRusSrbTranslation: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = navigateToAddSrbRusTranslation
        ) {
            Text(text = "Добавить перевод SRB -> RUS")
        }
        Button(
            onClick = navigateToAddRusSrbTranslation
        ) {
            Text(text = "Добавить перевод RUS -> SRB")
        }
    }
}

@Composable
@Preview(apiLevel = 33)
private fun AdminActionsScreenPreview() {
    AdminActionsScreen(
        viewModel = AdminActionsViewModel(),
        navigateToAddRusSrbTranslation = {},
        navigateToAddSrbRusTranslation = {}
    )
}