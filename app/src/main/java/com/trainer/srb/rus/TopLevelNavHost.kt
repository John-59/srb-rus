package com.trainer.srb.rus

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.trainer.srb.rus.feature.addword.AddWordScreen
import com.trainer.srb.rus.feature.addword.navigation.AddWordDestination
import com.trainer.srb.rus.feature.addword.navigation.navigateToAddWord
import com.trainer.srb.rus.feature.editword.EditWordScreen
import com.trainer.srb.rus.feature.editword.navigation.EditWordDestination
import com.trainer.srb.rus.feature.editword.navigation.navigateToEditWord
import com.trainer.srb.rus.feature.exercise.ExerciseScreen
import com.trainer.srb.rus.feature.exercise.navigation.ExerciseDestination
import com.trainer.srb.rus.feature.exercise.navigation.navigateToExercise

@Composable
fun TopLevelNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreenDestination.route
    ) {
        composable(MainScreenDestination.route) {
            MainScreen(
                navigateToExercise = navController::navigateToExercise,
                navigateToAddWord = navController::navigateToAddWord,
                navigateToEditWord = navController::navigateToEditWord
            )
        }
        composable(
            route = AddWordDestination.routeWithArgs,
            arguments = AddWordDestination.arguments
        ) {
            AddWordScreen(
                onBack = navController::navigateToMainScreen
            )
        }
        composable(
            route = EditWordDestination.routeWithArgs,
            arguments = EditWordDestination.arguments
        ) {
            EditWordScreen(
                onBack = navController::navigateToMainScreen
            )
        }
        composable(
            route = ExerciseDestination.routeWithArgs,
            arguments = ExerciseDestination.arguments
        ) {
            ExerciseScreen(
                onFinished = navController::navigateToMainScreen
            )
        }
    }
}