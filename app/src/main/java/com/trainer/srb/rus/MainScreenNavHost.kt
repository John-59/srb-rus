package com.trainer.srb.rus

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.trainer.srb.rus.feature.editword.navigation.navigateToEditWord
import com.trainer.srb.rus.feature.addword.navigation.navigateToAddWord
import com.trainer.srb.rus.feature.learn.LearnScreen
import com.trainer.srb.rus.feature.learn.navigation.LearnScreenDestination
import com.trainer.srb.rus.feature.dictionary.SearchScreen
import com.trainer.srb.rus.feature.dictionary.navigation.SearchScreenDestination

@Composable
fun MainScreenNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = LearnScreenDestination.route
    ) {
        composable(LearnScreenDestination.route) {
            LearnScreen()
        }
        composable(SearchScreenDestination.route) {
            SearchScreen(
                navigateToAddWord = {
                    navController.navigateToAddWord(
                        it,
                        SearchScreenDestination.route
                    )
                },
                navigateToEditWord = {
                    navController.navigateToEditWord(
                        it,
                        SearchScreenDestination.route
                    )
                }
            )
        }
//        composable(
//            route = AddWordDestination.routeWithArgs,
//            arguments = AddWordDestination.arguments
//        ) {
//            AddWordScreen(
//                onBack = {
//                    navController.navigateToSearch()//SearchScreenDestination.route)
//                }
//            )
//        }
//        composable(
//            route = EditWordDestination.routeWithArgs,
//            arguments = EditWordDestination.arguments
//        ) {
//            EditWordScreen(
//                onBack = {
//                    navController.navigateToSearch()//SearchScreenDestination.route)
//                }
//            )
//        }
    }
}