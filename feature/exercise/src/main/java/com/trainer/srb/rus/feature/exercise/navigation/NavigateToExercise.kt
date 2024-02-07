package com.trainer.srb.rus.feature.exercise.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToExercise() {
    val route = ExerciseDestination.route
    this.navigate(route) {
        launchSingleTop = true
//        popUpTo(
//            previousDestination
//        )
    }
}