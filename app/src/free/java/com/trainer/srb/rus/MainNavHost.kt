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
import com.trainer.srb.rus.feature.home.HomeScreen
import com.trainer.srb.rus.feature.home.navigation.HomeScreenDestination
import com.trainer.srb.rus.feature.search.SearchScreen
import com.trainer.srb.rus.feature.search.navigation.SearchScreenDestination
import com.trainer.srb.rus.feature.search.navigation.navigateToSearch

@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination.route
    ) {
        composable(HomeScreenDestination.route) {
            HomeScreen(
                navigateToSearch = navController::navigateToSearch
            )
        }
        composable(SearchScreenDestination.route) {
            SearchScreen()
        }

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