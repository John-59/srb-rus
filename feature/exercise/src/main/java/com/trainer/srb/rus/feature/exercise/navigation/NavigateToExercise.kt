package com.trainer.srb.rus.feature.exercise.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import com.trainer.srb.rus.feature.exercise.ExerciseType

fun NavHostController.navigateToExercise(exerciseType: ExerciseType, backRoute: String) {
    val exercise = Uri.encode(exerciseType.name)
    val route = "${ExerciseDestination.route}/$exercise"
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(backRoute)
    }
}