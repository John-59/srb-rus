package com.trainer.srb.rus.feature.addword.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.trainer.srb.rus.feature.addword.AddWordArgs

object AddWordDestination {
    const val route = "addword"
    const val routeWithArgs = route +
            "?{${AddWordArgs.srbLatValueArgName}}" +
            "?{${AddWordArgs.srbCyrValueArgName}}" +
            "?{${AddWordArgs.rusValueArgName}}"
    val arguments = listOf(
        navArgument(AddWordArgs.srbLatValueArgName) {
            type = NavType.StringType
            defaultValue = ""
        },
        navArgument(AddWordArgs.srbCyrValueArgName) {
            type = NavType.StringType
            defaultValue = ""
        },
        navArgument(AddWordArgs.rusValueArgName) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
}