package com.trainer.srb.rus.feature.about.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun NavHostController.navigateToAbout() {
    this.navigate(AboutScreenDestination.route) {
        popUpTo(
            graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}