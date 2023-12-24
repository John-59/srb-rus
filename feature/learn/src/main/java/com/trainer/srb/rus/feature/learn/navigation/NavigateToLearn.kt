package com.trainer.srb.rus.feature.learn.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToLearn(backRoute: String) {
    this.navigate(LearnDestination.route) {
        launchSingleTop = true
        popUpTo(backRoute)
    }
}