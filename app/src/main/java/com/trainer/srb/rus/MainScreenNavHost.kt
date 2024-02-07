package com.trainer.srb.rus

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.trainer.srb.rus.feature.addword.navigation.navigateToAddWord
import com.trainer.srb.rus.feature.dictionary.SearchScreen
import com.trainer.srb.rus.feature.editword.navigation.navigateToEditWord
import com.trainer.srb.rus.feature.learn.LearnScreen

@Composable
fun MainScreenNavHost(
    navController: NavHostController,
    navigateToExercise: () -> Unit,
    startDestination: MainScreenDestination.Inner
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(MainScreenDestination.Inner.LEARN.route) {
            LearnScreen(
                navigateToExercise = navigateToExercise
            )
        }
        composable(MainScreenDestination.Inner.DICTIONARY.route) {
            SearchScreen(
                navigateToAddWord = { word ->
                    navController.navigateToAddWord(
                        word,
                        MainScreenDestination.Inner.DICTIONARY.route
                    )
                },
                navigateToEditWord = { word ->
                    navController.navigateToEditWord(
                        word,
                        MainScreenDestination.Inner.DICTIONARY.route
                    )
                }
            )
        }
    }
}