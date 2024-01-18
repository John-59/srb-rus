package com.trainer.srb.rus.feature.learn.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.trainer.srb.rus.feature.learn.LearnArgs

object LearnDestination {
    const val route = "learn"
    const val routeWithArgs = "$route/{${LearnArgs.exerciseTypeArgName}}"
    val arguments = listOf(
        navArgument(LearnArgs.exerciseTypeArgName) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
}