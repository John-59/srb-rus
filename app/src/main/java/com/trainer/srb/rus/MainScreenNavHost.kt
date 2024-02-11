package com.trainer.srb.rus

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.feature.dictionary.SearchScreen
import com.trainer.srb.rus.feature.exercise.ExerciseType
import com.trainer.srb.rus.feature.learn.LearnScreen

@Composable
fun MainScreenNavHost(
    navController: NavHostController,
    navigateToExercise: (ExerciseType) -> Unit,
    navigateToAddWord: (Word) -> Unit,
    navigateToEditWord: (Translation<Word.Serbian, Word.Russian>) -> Unit,
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
                navigateToAddWord = navigateToAddWord,
                navigateToEditWord = navigateToEditWord
            )
        }
    }
}