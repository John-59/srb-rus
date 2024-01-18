package com.trainer.srb.rus

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.trainer.srb.rus.editword.EditWordScreen
import com.trainer.srb.rus.editword.navigation.EditWordDestination
import com.trainer.srb.rus.editword.navigation.navigateToEditWord
import com.trainer.srb.rus.feature.addword.AddWordScreen
import com.trainer.srb.rus.feature.addword.navigation.AddWordDestination
import com.trainer.srb.rus.feature.addword.navigation.navigateToAddWord
import com.trainer.srb.rus.feature.home.HomeScreen
import com.trainer.srb.rus.feature.home.navigation.HomeScreenDestination
import com.trainer.srb.rus.feature.home.navigation.navigateToHome
import com.trainer.srb.rus.feature.learn.LearnScreen
import com.trainer.srb.rus.feature.learn.navigation.LearnDestination
import com.trainer.srb.rus.feature.learn.navigation.navigateToLearn
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
                navigateToSearch = {
                    navController.navigateToSearch(HomeScreenDestination.route)
                },
                navigateToLearn = {
                    navController.navigateToLearn(it, HomeScreenDestination.route)
                }
            )
        }
        composable(
            route = LearnDestination.routeWithArgs,
            arguments = LearnDestination.arguments
        ) {
            LearnScreen(
                onFinished = navController::navigateToHome
            )
        }
        composable(SearchScreenDestination.route) {
            SearchScreen(
                navigateToAddWord = {
                    navController.navigateToAddWord(it, SearchScreenDestination.route)
                },
                navigateToEditWord = {
                    navController.navigateToEditWord(it, SearchScreenDestination.route)
                }
            )
        }
        composable(
            route = AddWordDestination.routeWithArgs,
            arguments = AddWordDestination.arguments
        ) {
            AddWordScreen(
                onBack = {
                    navController.navigateToSearch(SearchScreenDestination.route)
                }
            )
        }
        composable(
            route = EditWordDestination.routeWithArgs,
            arguments = EditWordDestination.arguments
        ) {
            EditWordScreen(
                onBack = {
                    navController.navigateToSearch(SearchScreenDestination.route)
                }
            )
        }
    }
}