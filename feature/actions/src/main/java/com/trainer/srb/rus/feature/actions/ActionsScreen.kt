package com.trainer.srb.rus.feature.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ActionsScreen() {
    Scaffold(
        topBar = {
            ActionsTopBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {}
            ) {
                Text("Искать")
            }
            Button(
                onClick = {}
            ) {
                Text("Добавить")
            }
            Button(
                onClick = {}
            ) {
                Text("Учить")
            }
        }
    }
}

@Composable
@Preview(apiLevel = 33)
fun ActionsScreenPreview() {
    ActionsScreen()
}