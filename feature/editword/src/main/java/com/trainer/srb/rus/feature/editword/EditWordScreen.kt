package com.trainer.srb.rus.feature.editword

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.mocks.DictionaryMock

@Composable
fun EditWordScreen(
    onBack: () -> Unit,
    viewModel: EditWordViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    EditWordBody(
        state = state,
        onBack = onBack,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    )
}

@Preview(apiLevel = 33)
@Composable
private fun EditWordScreenPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        EditWordScreen(
            onBack = {},
            viewModel = EditWordViewModel(
                savedStateHandle = SavedStateHandle(
                    initialState = mapOf(
                        EditWordArgs.latinValueIdArgName to 1L
                    )
                ),
                dictionary = DictionaryMock()
            )
        )
    }
}