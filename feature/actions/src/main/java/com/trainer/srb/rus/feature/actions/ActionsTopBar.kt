package com.trainer.srb.rus.feature.actions

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ActionsTopBar() {
    TopAppBar(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            modifier = Modifier.weight(2.0f),
            text =""
        )
        Button(
            onClick = {}
        ) {
            Text(
                text = "Настройки"
            )
        }
    }
}

@Composable
@Preview(apiLevel = 33)
private fun ActionsTopBarPreview() {
    ActionsTopBar()
}