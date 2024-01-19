package com.trainer.srb.rus.feature.home.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToHome() {
    this.navigate(HomeScreenDestination.route) {
        launchSingleTop = true
        popBackStack()
    }
}