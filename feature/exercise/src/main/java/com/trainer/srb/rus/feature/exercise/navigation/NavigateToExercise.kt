package com.trainer.srb.rus.feature.exercise.navigation

import android.net.Uri
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.trainer.srb.rus.core.exercise.ExerciseType

fun NavHostController.navigateToExercise(
    exerciseType: ExerciseType,
//    backRoute: String
) {
    val exercise = Uri.encode(exerciseType.name)
    val route = "${ExerciseDestination.route}/$exercise"
    this.navigate(route) {
//        popUpTo(backRoute)
        popUpTo(
            //previousDestination
            graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}