package com.trainer.srb.rus

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.trainer.srb.rus.feature.actions.ActionsScreen
import com.trainer.srb.rus.feature.actions.navigation.ActionsDestination
import com.trainer.srb.rus.feature.addword.AddWordScreen
import com.trainer.srb.rus.feature.addword.navigation.AddWordDestination
import com.trainer.srb.rus.feature.addword.navigation.navigateToAddWord
import com.trainer.srb.rus.feature.dictionary.DictionaryScreen
import com.trainer.srb.rus.feature.dictionary.navigation.DictionaryDestination
import com.trainer.srb.rus.feature.dictionary.navigation.navigateToDictionary

@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ActionsDestination.route
    ) {
        composable(ActionsDestination.route) {
            ActionsScreen(
                navigateToAddWord = navController::navigateToAddWord,
                navigateToDictionary = navController::navigateToDictionary
            )
        }
        composable(AddWordDestination.route) {
            AddWordScreen()
        }
        composable(DictionaryDestination.route) {
            DictionaryScreen()
        }
    }
}