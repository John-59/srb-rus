package com.trainer.srb.rus.feature.dictionary.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun NavHostController.navigateToSearch(
//    previousDestination: String
) {
    this.navigate(SearchScreenDestination.route) {
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