package com.trainer.srb.rus.feature.learn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LearnScreen(
     viewModel: LearnViewModel = hiltViewModel()
) {
    val learnState by viewModel.state.collectAsState()
    when (learnState) {
        LearnState.Initialize -> {
            InitializeBody()
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun LearnScreenPreview() {
    LearnScreen(
        viewModel = LearnViewModel()
    )
}