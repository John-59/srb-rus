package com.trainer.srb.rus.feature.learn.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToLearn() {
    this.navigate(LearnScreenDestination.route) {
        launchSingleTop = true
        popBackStack()
    }
}