package com.trainer.srb.rus

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.trainer.srb.rus.feature.addword.AddWordScreen
import com.trainer.srb.rus.feature.addword.navigation.AddWordDestination
import com.trainer.srb.rus.feature.editword.EditWordScreen
import com.trainer.srb.rus.feature.editword.navigation.EditWordDestination
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
                navigateToExercise = navController::navigateToExercise
            )
        }
        composable(AddWordDestination.route) {
            AddWordScreen {

            }
        }
        composable(EditWordDestination.route) {
            EditWordScreen(onBack = { /*TODO*/ })
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