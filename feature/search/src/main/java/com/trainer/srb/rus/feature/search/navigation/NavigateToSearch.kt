package com.trainer.srb.rus.feature.search.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToSearch(previousDestination: String) {
    this.navigate(SearchScreenDestination.route) {
        launchSingleTop = true
        popUpTo(
            previousDestination
        )
    }
}