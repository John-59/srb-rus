package com.trainer.srb.rus

import androidx.navigation.NavHostController

fun NavHostController.navigateToMainScreen() {
    this.navigate(MainScreenDestination.route) {
        launchSingleTop = true
        popBackStack()
    }
}