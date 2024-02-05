package com.trainer.srb.rus.feature.editword

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EditWordBody(
    state: EditWordState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is EditWordState.Error -> {
            EditWordErrorBody(
                message = state.message,
                modifier = modifier
            )
        }
        EditWordState.Initialize -> {
            EditWordInitializeBody(
                modifier = modifier
            )
        }
        is EditWordState.Success -> {
            EditWordSuccessBody(
                state = state,
                onBack = onBack,
                modifier = modifier
            )
        }
    }
}