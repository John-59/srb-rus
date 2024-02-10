package com.trainer.srb.rus.feature.exercise.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.trainer.srb.rus.feature.exercise.ExerciseArgs

object ExerciseDestination {
    const val route = "exercise"
    const val routeWithArgs = "$route/{${ExerciseArgs.exerciseTypeArgName}}"
    val arguments = listOf(
        navArgument(ExerciseArgs.exerciseTypeArgName) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
}