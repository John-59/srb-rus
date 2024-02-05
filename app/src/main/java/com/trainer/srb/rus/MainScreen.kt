package com.trainer.srb.rus

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
//    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                destinations = viewModel.destinations,
                currentDestination = viewModel.currentDestination,
                onDestinationChanged = {
                    viewModel.changeDestination(it, navController)
                }
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding)
        ) {
            MainNavHost(
                navController = navController
            )
        }
    }
}