package com.trainer.srb.rus

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.trainer.srb.rus.feature.actions.ActionsScreen
import com.trainer.srb.rus.feature.actions.navigation.ActionsDestination

@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ActionsDestination.route
    ) {
        composable(ActionsDestination.route) {
            ActionsScreen()
        }
    }
}