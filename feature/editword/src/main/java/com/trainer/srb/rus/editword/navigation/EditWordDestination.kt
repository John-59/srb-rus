package com.trainer.srb.rus.editword.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.trainer.srb.rus.editword.EditWordArgs

object EditWordDestination {
    const val route = "editword"
    const val routeWithArgs = "$route/{${EditWordArgs.latinValueIdArgName}}"
    val arguments = listOf(
        navArgument(EditWordArgs.latinValueIdArgName) {
            type = NavType.LongType
            defaultValue = -1
        }
    )
}