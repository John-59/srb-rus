package com.trainer.srb.rus.feature.admin

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AdminScreen() {
    Text("This screen only for administrator.")
}

@Composable
@Preview(apiLevel = 33)
private fun AdminScreenPreview() {
    AdminScreen()
}