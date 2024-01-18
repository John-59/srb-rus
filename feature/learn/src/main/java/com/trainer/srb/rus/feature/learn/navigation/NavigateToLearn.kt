package com.trainer.srb.rus.feature.learn.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import com.trainer.srb.rus.core.dictionary.ExerciseType

fun NavHostController.navigateToLearn(exerciseType: ExerciseType, backRoute: String) {
    val exercise = Uri.encode(exerciseType.name)
    val route = "${LearnDestination.route}/$exercise"
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(backRoute)
    }
}